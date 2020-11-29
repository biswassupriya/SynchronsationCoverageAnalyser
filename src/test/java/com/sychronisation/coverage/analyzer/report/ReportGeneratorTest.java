package com.sychronisation.coverage.analyzer.report;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sychronisation.coverage.analyzer.model.SynchronisationStatistics;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class ReportGeneratorTest {


    @Test
    public void testReportGenerator() throws IOException {

      //  Scanner scanner = new Scanner(new File("G:\\projects\\git\\SynchronsationCoverageAnalyser\\src\\test\\resources\\execution_results.txt"));

        List<SynchronisationStatistics> synchronisationStatistics = ReportGenerator.processExecutionResults("G:\\projects\\git\\SynchronsationCoverageAnalyser\\src\\test\\resources\\execution_results.txt");

        ObjectMapper objectMapper = new ObjectMapper();

        String actual = objectMapper.writeValueAsString(synchronisationStatistics);
        List<SynchronisationStatistics> expectedObject = objectMapper.readValue(expectedResult(), new TypeReference<List<SynchronisationStatistics>>(){});
        String expected = objectMapper.writeValueAsString(expectedObject);

        Assert.assertEquals(actual, expected);

    }

    private String expectedResult(){
        return "[\n" +
                "  {\n" +
                "    \"classStatistics\": {\n" +
                "      \"className\": \"com.library.system.model.SynchronisedBookIdGenerator\",\n" +
                "      \"methodStatistics\": [\n" +
                "        {\n" +
                "          \"methodName\": \"increment\",\n" +
                "          \"synchronised\": true\n" +
                "        },\n" +
                "        {\n" +
                "          \"methodName\": \"resetCounter\",\n" +
                "          \"synchronised\": false\n" +
                "        }\n" +
                "      ],\n" +
                "      \"runnable\": false\n" +
                "    },\n" +
                "    \"threadSafetyCheck\": \"Not Applicable\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"classStatistics\": {\n" +
                "      \"className\": \"com.library.system.service.BookCreator\",\n" +
                "      \"methodStatistics\": [\n" +
                "        {\n" +
                "          \"methodName\": \"run\",\n" +
                "          \"synchronised\": false\n" +
                "        }\n" +
                "      ],\n" +
                "      \"runnable\": true\n" +
                "    },\n" +
                "    \"threadSafetyCheck\": \"Not Applicable\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"classStatistics\": {\n" +
                "      \"className\": \"com.library.system.model.UserIdGenerator\",\n" +
                "      \"methodStatistics\": [\n" +
                "        {\n" +
                "          \"methodName\": \"increment\",\n" +
                "          \"synchronised\": false\n" +
                "        },\n" +
                "        {\n" +
                "          \"methodName\": \"resetCounter\",\n" +
                "          \"synchronised\": false\n" +
                "        }\n" +
                "      ],\n" +
                "      \"runnable\": false\n" +
                "    },\n" +
                "    \"threadSafetyCheck\": \"Not Applicable\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"classStatistics\": {\n" +
                "      \"className\": \"com.library.system.model.Book\",\n" +
                "      \"methodStatistics\": [\n" +
                "        {\n" +
                "          \"methodName\": \"setIsbn\",\n" +
                "          \"synchronised\": false\n" +
                "        },\n" +
                "        {\n" +
                "          \"methodName\": \"getIsbn\",\n" +
                "          \"synchronised\": false\n" +
                "        },\n" +
                "        {\n" +
                "          \"methodName\": \"setBookName\",\n" +
                "          \"synchronised\": false\n" +
                "        },\n" +
                "        {\n" +
                "          \"methodName\": \"getBookName\",\n" +
                "          \"synchronised\": false\n" +
                "        },\n" +
                "        {\n" +
                "          \"methodName\": \"setUser\",\n" +
                "          \"synchronised\": false\n" +
                "        },\n" +
                "        {\n" +
                "          \"methodName\": \"getUser\",\n" +
                "          \"synchronised\": false\n" +
                "        },\n" +
                "        {\n" +
                "          \"methodName\": \"getDamageDescription\",\n" +
                "          \"synchronised\": false\n" +
                "        },\n" +
                "        {\n" +
                "          \"methodName\": \"isBookAvailable\",\n" +
                "          \"synchronised\": false\n" +
                "        },\n" +
                "        {\n" +
                "          \"methodName\": \"getBookDetailsByIsbn\",\n" +
                "          \"synchronised\": false\n" +
                "        },\n" +
                "        {\n" +
                "          \"methodName\": \"getBookDetail\",\n" +
                "          \"synchronised\": false\n" +
                "        },\n" +
                "        {\n" +
                "          \"methodName\": \"equals\",\n" +
                "          \"synchronised\": false\n" +
                "        },\n" +
                "        {\n" +
                "          \"methodName\": \"hashCode\",\n" +
                "          \"synchronised\": false\n" +
                "        }\n" +
                "      ],\n" +
                "      \"runnable\": false\n" +
                "    },\n" +
                "    \"threadSafetyCheck\": \"Not Applicable\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"classStatistics\": {\n" +
                "      \"className\": \"com.library.system.model.Library\",\n" +
                "      \"methodStatistics\": [\n" +
                "        {\n" +
                "          \"methodName\": \"getBooks\",\n" +
                "          \"synchronised\": false\n" +
                "        },\n" +
                "        {\n" +
                "          \"methodName\": \"setBook\",\n" +
                "          \"synchronised\": false\n" +
                "        },\n" +
                "        {\n" +
                "          \"methodName\": \"getUsers\",\n" +
                "          \"synchronised\": false\n" +
                "        },\n" +
                "        {\n" +
                "          \"methodName\": \"setUser\",\n" +
                "          \"synchronised\": false\n" +
                "        }\n" +
                "      ],\n" +
                "      \"runnable\": false\n" +
                "    },\n" +
                "    \"threadSafetyCheck\": \"Not Applicable\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"classStatistics\": {\n" +
                "      \"className\": \"com.library.system.service.UserService\",\n" +
                "      \"methodStatistics\": [\n" +
                "        {\n" +
                "          \"methodName\": \"addUsers\",\n" +
                "          \"synchronised\": false\n" +
                "        }\n" +
                "      ],\n" +
                "      \"runnable\": false\n" +
                "    },\n" +
                "    \"threadSafetyCheck\": \"failed\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"classStatistics\": {\n" +
                "      \"className\": \"com.library.system.service.BookService\",\n" +
                "      \"methodStatistics\": [\n" +
                "        {\n" +
                "          \"methodName\": \"addBooks\",\n" +
                "          \"synchronised\": false\n" +
                "        }\n" +
                "      ],\n" +
                "      \"runnable\": false\n" +
                "    },\n" +
                "    \"threadSafetyCheck\": \"passed\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"classStatistics\": {\n" +
                "      \"className\": \"com.library.system.service.UserCreator\",\n" +
                "      \"methodStatistics\": [\n" +
                "        {\n" +
                "          \"methodName\": \"run\",\n" +
                "          \"synchronised\": false\n" +
                "        }\n" +
                "      ],\n" +
                "      \"runnable\": true\n" +
                "    },\n" +
                "    \"threadSafetyCheck\": \"Not Applicable\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"classStatistics\": {\n" +
                "      \"className\": \"com.library.system.model.User\",\n" +
                "      \"methodStatistics\": [\n" +
                "        {\n" +
                "          \"methodName\": \"getUserId\",\n" +
                "          \"synchronised\": false\n" +
                "        },\n" +
                "        {\n" +
                "          \"methodName\": \"setUserId\",\n" +
                "          \"synchronised\": false\n" +
                "        },\n" +
                "        {\n" +
                "          \"methodName\": \"getUserName\",\n" +
                "          \"synchronised\": false\n" +
                "        },\n" +
                "        {\n" +
                "          \"methodName\": \"setUserName\",\n" +
                "          \"synchronised\": false\n" +
                "        },\n" +
                "        {\n" +
                "          \"methodName\": \"getBooksBorrowed\",\n" +
                "          \"synchronised\": false\n" +
                "        },\n" +
                "        {\n" +
                "          \"methodName\": \"setBooksBorrowed\",\n" +
                "          \"synchronised\": false\n" +
                "        },\n" +
                "        {\n" +
                "          \"methodName\": \"getMessages\",\n" +
                "          \"synchronised\": false\n" +
                "        },\n" +
                "        {\n" +
                "          \"methodName\": \"setMessages\",\n" +
                "          \"synchronised\": false\n" +
                "        },\n" +
                "        {\n" +
                "          \"methodName\": \"appendMessage\",\n" +
                "          \"synchronised\": false\n" +
                "        },\n" +
                "        {\n" +
                "          \"methodName\": \"getAllMessagesByUserId\",\n" +
                "          \"synchronised\": false\n" +
                "        },\n" +
                "        {\n" +
                "          \"methodName\": \"getAllBooksByUserId\",\n" +
                "          \"synchronised\": false\n" +
                "        }\n" +
                "      ],\n" +
                "      \"runnable\": false\n" +
                "    },\n" +
                "    \"threadSafetyCheck\": \"Not Applicable\"\n" +
                "  }\n" +
                "]\n";
    }



}