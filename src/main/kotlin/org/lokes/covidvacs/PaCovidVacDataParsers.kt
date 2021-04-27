package org.lokes.covidvacs

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.node.ArrayNode
import kong.unirest.Unirest
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import java.io.File
import java.nio.file.Files
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.system.exitProcess


@SpringBootApplication
@EnableWebMvc
class PaCovidVacDataParsers(private val data: Data) : CommandLineRunner {
    override fun run(vararg args: String?) {
       // data.getData()
    }

}

fun main(args: Array<String>) {
    runApplication<PaCovidVacDataParsers>(*args)
}

@Configuration
class DataBeans {
    @Bean
    fun objectMapper(): ObjectMapper = ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT)
}

@Component
class Data(private val objectMapper: ObjectMapper) {

    private val requestBody =
            """
         {
             "version": "1.0.0",
             "queries": [
                 {
                     "Query": {
                         "Commands": [
                             {
                                 "SemanticQueryDataShapeCommand": {
                                     "Query": {
                                         "Version": 2,
                                         "From": [
                                             {
                                                 "Name": "f",
                                                 "Entity": "Counts of People by County",
                                                 "Type": 0
                                             }
                                         ],
                                         "Select": [
                                             {
                                                 "Column": {
                                                     "Expression": {
                                                         "SourceRef": {
                                                             "Source": "f"
                                                         }
                                                     },
                                                     "Property": "County"
                                                 },
                                                 "Name": "Counts of People by County.County"
                                             },
                                             {
                                                 "Aggregation": {
                                                     "Expression": {
                                                         "Column": {
                                                             "Expression": {
                                                                 "SourceRef": {
                                                                     "Source": "f"
                                                                 }
                                                             },
                                                             "Property": "PartiallyCovered"
                                                         }
                                                     },
                                                     "Function": 0
                                                 },
                                                 "Name": "Sum(Counts of People by County.PartiallyCovered)"
                                             },
                                             {
                                                 "Aggregation": {
                                                     "Expression": {
                                                         "Column": {
                                                             "Expression": {
                                                                 "SourceRef": {
                                                                     "Source": "f"
                                                                 }
                                                             },
                                                             "Property": "FullyCovered"
                                                         }
                                                     },
                                                     "Function": 0
                                                 },
                                                 "Name": "Sum(Counts of People by County.FullyCovered)"
                                             }
                                         ],
                                         "Where": [
                                             {
                                                 "Condition": {
                                                     "Not": {
                                                         "Expression": {
                                                             "In": {
                                                                 "Expressions": [
                                                                     {
                                                                         "Column": {
                                                                             "Expression": {
                                                                                 "SourceRef": {
                                                                                     "Source": "f"
                                                                                 }
                                                                             },
                                                                             "Property": "County"
                                                                         }
                                                                     }
                                                                 ],
                                                                 "Values": [
                                                                     [
                                                                         {
                                                                             "Literal": {
                                                                                 "Value": "null"
                                                                             }
                                                                         }
                                                                     ],
                                                                     [
                                                                         {
                                                                             "Literal": {
                                                                                 "Value": "''"
                                                                             }
                                                                         }
                                                                     ],
                                                                     [
                                                                         {
                                                                             "Literal": {
                                                                                 "Value": "'Out-of-State'"
                                                                             }
                                                                         }
                                                                     ]
                                                                 ]
                                                             }
                                                         }
                                                     }
                                                 }
                                             }
                                         ],
                                         "OrderBy": [
                                             {
                                                 "Direction": 2,
                                                 "Expression": {
                                                     "Aggregation": {
                                                         "Expression": {
                                                             "Column": {
                                                                 "Expression": {
                                                                     "SourceRef": {
                                                                         "Source": "f"
                                                                     }
                                                                 },
                                                                 "Property": "PartiallyCovered"
                                                             }
                                                         },
                                                         "Function": 0
                                                     }
                                                 }
                                             }
                                         ]
                                     },
                                     "Binding": {
                                         "Primary": {
                                             "Groupings": [
                                                 {
                                                     "Projections": [
                                                         0,
                                                         1,
                                                         2
                                                     ]
                                                 }
                                             ]
                                         },
                                         "DataReduction": {
                                             "DataVolume": 4,
                                             "Primary": {
                                                 "Top": {}
                                             }
                                         },
                                         "Aggregates": [
                                             {
                                                 "Select": 1,
                                                 "Aggregations": [
                                                     {
                                                         "Min": {}
                                                     },
                                                     {
                                                         "Max": {}
                                                     }
                                                 ]
                                             }
                                         ],
                                         "SuppressedJoinPredicates": [
                                             2
                                         ],
                                         "Version": 1
                                     }
                                 }
                             }
                         ]
                     },
                     "CacheKey": "{\"Commands\":[{\"SemanticQueryDataShapeCommand\":{\"Query\":{\"Version\":2,\"From\":[{\"Name\":\"f\",\"Entity\":\"Counts of People by County\",\"Type\":0}],\"Select\":[{\"Column\":{\"Expression\":{\"SourceRef\":{\"Source\":\"f\"}},\"Property\":\"County\"},\"Name\":\"Counts of People by County.County\"},{\"Aggregation\":{\"Expression\":{\"Column\":{\"Expression\":{\"SourceRef\":{\"Source\":\"f\"}},\"Property\":\"PartiallyCovered\"}},\"Function\":0},\"Name\":\"Sum(Counts of People by County.PartiallyCovered)\"},{\"Aggregation\":{\"Expression\":{\"Column\":{\"Expression\":{\"SourceRef\":{\"Source\":\"f\"}},\"Property\":\"FullyCovered\"}},\"Function\":0},\"Name\":\"Sum(Counts of People by County.FullyCovered)\"}],\"Where\":[{\"Condition\":{\"Not\":{\"Expression\":{\"In\":{\"Expressions\":[{\"Column\":{\"Expression\":{\"SourceRef\":{\"Source\":\"f\"}},\"Property\":\"County\"}}],\"Values\":[[{\"Literal\":{\"Value\":\"null\"}}],[{\"Literal\":{\"Value\":\"''\"}}],[{\"Literal\":{\"Value\":\"'Out-of-State'\"}}]]}}}}}],\"OrderBy\":[{\"Direction\":2,\"Expression\":{\"Aggregation\":{\"Expression\":{\"Column\":{\"Expression\":{\"SourceRef\":{\"Source\":\"f\"}},\"Property\":\"PartiallyCovered\"}},\"Function\":0}}}]},\"Binding\":{\"Primary\":{\"Groupings\":[{\"Projections\":[0,1,2]}]},\"DataReduction\":{\"DataVolume\":4,\"Primary\":{\"Top\":{}}},\"Aggregates\":[{\"Select\":1,\"Aggregations\":[{\"Min\":{}},{\"Max\":{}}]}],\"SuppressedJoinPredicates\":[2],\"Version\":1}}}]}",
                     "QueryId": "",
                     "ApplicationContext": {
                         "DatasetId": "1e549164-aeab-4dcf-a97f-f70de27e715d",
                         "Sources": [
                             {
                                 "ReportId": "53c72848-c634-4597-a571-54c087a01780"
                             }
                         ]
                     }
                 }
             ],
             "cancelQueries": [],
             "modelId": 314528
         }
        """.trimIndent()

    private val headers: Map<String, String> = mapOf(
            "Accept" to "application/json",
            "Content-Type" to "application/json",
            "X-PowerBI-ResourceKey" to "da0f9423-f4be-4676-b242-4ecf4f49d002"
    )

    fun getData(): List<String> {

        val response = Unirest.post("https://wabi-us-gov-iowa-api.analysis.usgovcloudapi.net/public/reports/querydata")
                .headers(headers)
                .body(requestBody).asString()

        if (!response.isSuccess || response.body == null) {
            System.err.println("Unable to download PA data file")
            exitProcess(1)
        }

        println("Successfully downloaded PA data file and converting it to a CSV")


        val fileContent: MutableList<String> = mutableListOf()
        fileContent.add("\"county\"|\"partially covered\"|\"fully covered\"")

        try {
            val root: JsonNode = objectMapper.readTree(response.body)

            val data = root.get("results").get(0).get("result").get("data").get("dsr").get("DS").get(0)

            val counties: MutableMap<Int, String> = mutableMapOf()
            data.get("ValueDicts").get("D0").forEachIndexed { i, e ->
                counties[i] = e.asText()
            }


            (data.get("PH").get(0).get("DM0") as ArrayNode).forEach {
                val county = (it.get("C") as ArrayNode)
                if (it.get("R") != null && it.get("R").asInt() == 4) {
                    fileContent.add("\"${counties[county.get(0).asInt()]}\"|${county.get(1).asInt()}|\"<Unavailable -- see state dashboard>\"")
                } else if (it.get("R") != null && it.get("R").asInt() == 2) {
                    fileContent.add("\"${counties[county.get(0).asInt()]}\"|\"<Unavailable -- see state dashboard>\"|${county.get(1).asInt()}")
                } else {
                    fileContent.add("\"${counties[county.get(0).asInt()]}\"|${county.get(1).asInt()}|${county.get(2).asInt()}")
                }
            }
            return fileContent
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    fun csv() {
        val file = File(
                System.getProperty("user.home"),
                "Desktop/pa_counties_covid_vaccine_count_${
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"))
                }.csv"
        )

        try {
            Files.createFile(file.toPath())
            val data = getData()
            Files.write(file.toPath(), data.joinToString("\n").toByteArray())
            println("Done. Results file:" + file.absolutePath)
        } catch (e: java.lang.Exception) {
            System.err.println("Unable to create CSV file: " + e.message)
            exitProcess(1)
        }
    }
}

