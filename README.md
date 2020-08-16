# SychronisationCoverageAnalyzer
Analyses the Code, instrument it and produces a Sychronisation Coverage report

#Code instrumentation and execution
#Usage
java -jar <path_to_jar>/SychronisationCoverageAnalyzer-1.0-SNAPSHOT-jar-with-dependencies.jar <path_to_jar_which_needs_instrumented>/<jar>
#Example:
 java -javaagent:G:/projects/git/SynchronsationCoverageAnalyser/target/SychronisationCoverageAnalyzer-1.0-SNAPSHOT-jar-with-dependencies.jar -jar G:/projects/git/library-system/target/libary-management-system-1.0-SNAPSHOT-tests.jar >> execution_results.txt 2>&1


#ReportGeneration
#Usage
java -jar <path_to_jar>/SychronisationCoverageAnalyzer-1.0-SNAPSHOT-jar-with-dependencies.jar <path_to_execution_results>/execution_results.txt
#Example:
 java -jar G:/projects/git/SynchronsationCoverageAnalyser/target/SychronisationCoverageAnalyzer-1.0-SNAPSHOT-jar-with-dependencies.jar  G:/projects/git/CodeCoverageAnalyzer/instrumented/execution_results.txt
-----------------------------------------------------------------------------------------------------------------------------------------------
ClassName                                                      Single-Thread(1)       Multi-Thread(10)       Concurrency-Coverage-Percentage(%)
-----------------------------------------------------------------------------------------------------------------------------------------------
com.library.system.service.BookInventoryServiceTest                100                      0                    50
com.library.system.service.BookInventorySynchronisedServiceTest    100                    100                    100
-----------------------------------------------------------------------------------------------------------------------------------------------
