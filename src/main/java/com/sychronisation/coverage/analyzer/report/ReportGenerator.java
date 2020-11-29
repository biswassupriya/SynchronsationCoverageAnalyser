package com.sychronisation.coverage.analyzer.report;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sychronisation.coverage.analyzer.model.ClassStatistics;
import com.sychronisation.coverage.analyzer.model.SynchronisationStatistics;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Report Generator produces the concurrency coverage metric.
 * <p>
 * It parses the execution results and produces a report which shows the below attributes
 * <p>
 * ClassName            Name of Class for which the metric is produced
 * MethodName           Names of all methods in the class
 * isSynchronised       Boolean value indicating whether the method is synchronised.
 * isRunnable           Boolean value indicating whether implements the Runnable Interface
 * ThreadSafetyCheck    Indicates whether the Runnable class was able to call the synchronised methods with thread safety.
 * Possible values passed/failed/not applicable
 * <p>
 * <p>
 * Example
 * ClassName                                                   MethodName      isSynchronised      isRunnable       threadSafetyCheck
 * com.library.system.model.Library                               setUser               false           false          Not Applicable
 * com.library.system.service.UserService                        addUsers               false           false                  failed
 * com.library.system.service.BookService                        addBooks               false           false                  passed
 */

public class ReportGenerator {
    public static void main(String[] args) throws IOException {

        List<SynchronisationStatistics> synchronisationStatistics = processExecutionResults(args[0]);

        generateReport(synchronisationStatistics);

    }

    /**
     * method helps generating the report
     */
    private static void generateReport(List<SynchronisationStatistics> synchronisationStatistics) {
        String className = "ClassName";
        String methodName = "MethodName";
        String isSynchronised = "isSynchronised";
        String isRunnable = "isRunnable";
        String threadSafetyCheck = "threadSafetyCheck";
        System.out.format("%1s%60s%20s%16s%25s\n", className, methodName, isSynchronised, isRunnable, threadSafetyCheck);

        int numberOfClasses=synchronisationStatistics.size();
        ArrayList<String> totalNumberofMethods=new ArrayList<>();
        ArrayList<String > totalNumberofSynchronisedMethods=new ArrayList<>();

        synchronisationStatistics.forEach(stats -> {
            stats.getClassStatistics().getMethodStatistics().forEach(methodStats -> {

                int methodNameFormating = 70 - stats.getClassStatistics().getClassName().length();

                System.out.format("%1s%" + methodNameFormating + "s%20s%16s%20s\n", stats.getClassStatistics().getClassName()
                        , methodStats.getMethodName()
                        , methodStats.isSynchronised()
                        , stats.getClassStatistics().getRunnable()
                        , stats.getThreadSafetyCheck()
                );
                totalNumberofMethods.add(methodStats.getMethodName());
                if(methodStats.isSynchronised()) {
                    totalNumberofSynchronisedMethods.add(methodStats.getMethodName());
                }
            });
        });

        double d = (totalNumberofSynchronisedMethods.size() * 100 )/totalNumberofMethods.size();
        System.out.format("\n Total Number Of Classes: " + numberOfClasses);
        System.out.format("\n Total Number Of Methods: " + totalNumberofMethods.size());
        System.out.format("\n Total Number Of Synchronised Methods: " + totalNumberofSynchronisedMethods.size());
        System.out.format("\n Synchronised Method Percentage: " + d + " percent");


    }

    /**
     * method parses the execution result file and extract the test results
     *
     * @return
     */
    public static List<SynchronisationStatistics> processExecutionResults(String filename) throws IOException {

        Scanner scanner = new Scanner(new File(filename));
        scanner.useDelimiter("\r\n");

        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, ClassStatistics> stringClassStatisticsHashMap = new HashMap<>();
        HashMap<String, String> concurrencyResultsMap = new HashMap<>();


        while (scanner.hasNext()) {
            String line = scanner.next();
            if (line.contains("className")) {
                if (line.startsWith(".")) {
                    String replace = line.replace(".", "");
                    ClassStatistics classStatistics = objectMapper.readValue(replaceSlashToDots(replace), ClassStatistics.class);
                    stringClassStatisticsHashMap.put(classStatistics.getClassName(), classStatistics);
                } else {
                    ClassStatistics classStatistics = objectMapper.readValue(replaceSlashToDots(line), ClassStatistics.class);
                    stringClassStatisticsHashMap.put(classStatistics.getClassName(), classStatistics);
                }
            }


            if (line.contains("testConcurrency") && (line.contains("passed") || line.contains("failed"))) {
                String result = line.split("-")[2];
                String className = line.split("-")[0];
                concurrencyResultsMap.put(className, result);
            }
        }
        scanner.close();

        List<SynchronisationStatistics> synchronisationStatistics = new ArrayList<>();

        stringClassStatisticsHashMap.keySet().forEach(className -> {
            if (concurrencyResultsMap.containsKey(className)) {
                synchronisationStatistics.add(new SynchronisationStatistics(stringClassStatisticsHashMap.get(className), concurrencyResultsMap.get(className)));
            } else {
                synchronisationStatistics.add(new SynchronisationStatistics(stringClassStatisticsHashMap.get(className), "Not Applicable"));
            }
        });

        return synchronisationStatistics;

    }

    private static String replaceSlashToDots(final String line) {
        return line.replace('/', '.');
    }
}
