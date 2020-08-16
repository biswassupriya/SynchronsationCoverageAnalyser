package com.sychronisation.coverage.analyzer.report;
import com.sychronisation.coverage.analyzer.model.Coverage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Report Generator produces the concurrency coverage percentage metric
 * @parces the execution results
 * @produces a report based on the test result */

public class ReportGenerator {
    public static void main(String[] args) throws FileNotFoundException {
        List<String> arrayList = readFile(args[0]);

        int keyLength = 0;
        HashMap<String, Coverage> stringCoverageHashMap = new HashMap<>();
        for (String list : arrayList) {
            String[] split = list.split(":");
            getEntrySet(stringCoverageHashMap, split);
            if (keyLength < split[0].length()) {
                keyLength = split[0].length();
            }
        }
        generateReport(keyLength, stringCoverageHashMap);

    }

    private static void getEntrySet(HashMap<String, Coverage> stringCoverageHashMap, String[] split) {
        if (!stringCoverageHashMap.containsKey(split[0])) {
            Coverage coverage = getCoverage(split, new Coverage());
            stringCoverageHashMap.put(split[0], coverage);
        } else {
            Coverage coverage = stringCoverageHashMap.get(split[0]);
            Coverage coverage1 = getCoverage(split, coverage);
            stringCoverageHashMap.put(split[0], coverage1);
        }
    }

    /** method helps generating the report */
    private static void generateReport(int keyLength, HashMap<String, Coverage> stringCoverageHashMap) {
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("ClassName                                                      Single-Thread(1)       Multi-Thread(10)       Concurrency-Coverage-Percentage(%)");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------");
        int finalKeyLength = keyLength;
        stringCoverageHashMap.entrySet()
                .stream()
                .forEach(entry -> {
                    Coverage value = entry.getValue();
                    value.setPercentage((value.getMulti() + value.getSingle()) / 2);
                    entry.setValue(value);
                    String padding = "                    ";
                    String multiPadding = padding;
                    if (entry.getValue().getMulti() == 0) {
                        multiPadding = padding + "  ";
                    }
                    System.out.println(entry.getKey() + addPadding(entry.getKey().length(), finalKeyLength) + entry.getValue().getSingle() + multiPadding + entry.getValue().getMulti() + padding + entry.getValue().getPercentage());
                });
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------");
    }

    /** method parses the executio results files and extract the test results */
    private static List<String> readFile(String arg) throws FileNotFoundException {
        String patternString1 = "((Test-testConcurrency)(.+?))";

        Pattern pattern = Pattern.compile(patternString1);

        Scanner scanner = new Scanner(new File(arg));
        scanner.useDelimiter("\r\n");

        List<String> arrayList = new ArrayList();
        while (scanner.hasNext()) {
            String line = scanner.next();
            Matcher matcher = pattern.matcher(line);
            if(matcher.matches()) {
                String thread=line.split("_")[2];
                String result = line.split("-")[2];
                String className=line.split("\\(")[1].split("\\)")[0].split("Test")[0];
                String newLine=className + ":" + thread + ":" + result;
                arrayList.add(newLine);
            }
        }
        scanner.close();

        return arrayList;
    }

    private static String addPadding(int length, int finalKeyLength) {

        int spacesRequired = 4;
        StringBuilder sb = new StringBuilder();
        if (length < finalKeyLength) {
            spacesRequired += finalKeyLength - length;
        }
        for (int i = 0; i < spacesRequired; i++) {
            sb.append(' ');
        }
        return sb.toString();
    }

    private static Coverage getCoverage(String[] split, Coverage coverage) {
        Coverage coverage1 = coverage;
        if (split[1].contains("withSingleThread") && split[2].contains("passed")) {
            coverage1.setSingle(100);
        } else if (split[1].contains("withSingleThread") && split[2].contains("failed")) {
            coverage1.setSingle(0);
        }
        if (split[1].contains("withMultipleThread") && split[2].contains("passed")) {
            coverage1.setMulti(100);
        } else if (split[1].contains("withMultipleThread") && split[2].contains("failed")) {
            coverage1.setMulti(0);
        }
        return coverage1;
    }
}
