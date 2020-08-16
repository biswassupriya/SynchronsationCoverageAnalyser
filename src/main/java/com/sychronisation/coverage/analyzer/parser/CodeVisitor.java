package com.sychronisation.coverage.analyzer.parser;

import japa.parser.ASTHelper;
import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.Node;
import japa.parser.ast.expr.IntegerLiteralExpr;
import japa.parser.ast.expr.MethodCallExpr;
import japa.parser.ast.expr.NameExpr;
import japa.parser.ast.expr.StringLiteralExpr;
import japa.parser.ast.stmt.BlockStmt;
import japa.parser.ast.stmt.ExpressionStmt;
import japa.parser.ast.stmt.Statement;
import japa.parser.ast.stmt.SynchronizedStmt;
import japa.parser.ast.visitor.ModifierVisitorAdapter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CodeVisitor extends ModifierVisitorAdapter<Object> {

    private String filename;

    /**
     * Maps filenames and line numbers to true (executed) or false (not executed).
     */
    private static Map<String, Map<Integer, Boolean>> coverage =
            new HashMap<String, Map<Integer, Boolean>>();

    public CodeVisitor(String filename) {
        this.filename = filename;
    }

    /**
     * traverses block statement and adds instrumentation code.
     */
    @Override
    public Node visit(ExpressionStmt node, Object arg) {
        BlockStmt block = new BlockStmt();
        ASTHelper.addStmt(block, node);
        ASTHelper.addStmt(block, makeCoverageTrackingCall(filename, node.getBeginLine()));
        return block;
    }

    /**
     * traverses synchronised statement and adds instrumentation code.
     */
    @Override
    public Node visit(SynchronizedStmt node, Object arg) {
        BlockStmt block = new BlockStmt();
        ASTHelper.addStmt(block, node);
        ASTHelper.addStmt(block, makeCoverageTrackingCall(filename, node.getBeginLine()));
        return block;
    }

    /**
     * makeCoverageTrackingCall just creates a call to markExecuted,
     * with the filename and line number as arguments
     */
    private Statement makeCoverageTrackingCall(String filename, int line) {
        CodeVisitor.markExecutable(filename, line);
        NameExpr coverageTracker = ASTHelper.createNameExpr("CodeVisitor");
        MethodCallExpr call = new MethodCallExpr(coverageTracker, "markExecuted");
        ASTHelper.addArgument(call, new StringLiteralExpr(filename));
        ASTHelper.addArgument(call, new IntegerLiteralExpr(String.valueOf(line)));
        return new ExpressionStmt(call);
    }

    /**
     * javaparser has methods to modify ASTs,
     * in the form of a special visitor implementation called ModifierVisitorAdapter,
     * which has each visit method return an AST node
     */
    public static void main(String[] args) throws IOException, ParseException {
        File file = new File(args[0]);
        CompilationUnit unit = JavaParser.parse(new FileReader(file));
        unit.accept(new CodeVisitor(file.getAbsolutePath()), null);
        System.out.println(unit.toString());
    }


    /**
     * Marks the line as executable
     */
    public static void markExecutable(String filename, int line) {
        if (!coverage.containsKey(filename)) {
            coverage.put(filename, new HashMap<Integer, Boolean>());
        }
        coverage.get(filename).put(line, false);
    }

    /**
     * Marks the line as executed, called from the instrumented execution
     */
    public static void markExecuted(String filename, int line) {
        if (!coverage.containsKey(filename)) {
            coverage.put(filename, new HashMap<Integer, Boolean>());
        }
        coverage.get(filename).put(line, true);
    }

    /** JVM shutdown hook, to indicate program termination */
    static {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                writeCoverageToFile();
            }
        });
    }

    /**
     * method to serialise the coverage to file
     */
    private static void writeCoverageToFile() {
        String lcovCoverage = generateLcov();
        String coverageReportPath = System.getProperty("coverage.report.path", "coverage_report.lcov");
        FileWriter writer = null;
        try {
            writer = new FileWriter(coverageReportPath);
            writer.write(lcovCoverage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * method generating coverage
     */
    private static String generateLcov() {
        StringBuilder sb = new StringBuilder();
        for (String filename : coverage.keySet()) {
            sb.append("SF:" + filename + "\n");
            for (Map.Entry<Integer, Boolean> line : coverage.get(filename).entrySet()) {
                sb.append(String.format("DA:%d,%d\n", line.getKey(), line.getValue() ? 1 : 0));
            }
            sb.append("end_of_record\n");
        }
        return sb.toString();
    }

}