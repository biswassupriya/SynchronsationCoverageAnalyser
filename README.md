# SychronisationCoverageAnalyzer
Analyses the Code, instrument it and produces a Sychronization Coverage report

#Build Instructions
mvn clean install


#Code instrumentation and execution
#Usage
java -jar <path_to_jar>/SychronisationCoverageAnalyzer-1.0-SNAPSHOT-jar-with-dependencies.jar="packages=<package to scan for instrumentation>" -jar <path_to_jar_which_needs_instrumented>/<jar>
#Example:
java -javaagent:G:/projects/git/SynchronsationCoverageAnalyser/target/SychronisationCoverageAnalyzer-1.0-SNAPSHOT-jar-with-dependencies.jar="packages=com.library.system" -jar G:/projects/git/library-system/target/library-management-system-1.0-SNAPSHOT-tests.jar >> execution_results.txt 2>&1


#ReportGeneration
#Usage
java -jar <path_to_jar>/SychronisationCoverageAnalyzer-1.0-SNAPSHOT-jar-with-dependencies.jar <path_to_execution_results>/execution_results.txt
#Example:
 java -jar G:/projects/git/SynchronsationCoverageAnalyser/target/SychronisationCoverageAnalyzer-1.0-SNAPSHOT-jar-with-dependencies.jar  G:/projects/git/CodeCoverageAnalyzer/instrumented/execution_results.txt

#Example - OutPut
 
 # ClassName                                                   MethodName      isSynchronised      isRunnable       threadSafetyCheck
 # com.library.system.model.Library                               setUser               false           false          Not Applicable
 # com.library.system.service.UserService                        addUsers               false           false                  failed
 # com.library.system.service.BookService                        addBooks               false           false                  passed
 # com.library.system.service.BookCreator                             run               false            true          Not Applicable
 # com.library.system.model.SynchronisedBookIdGenerator         increment                true           false          Not Applicable
