package org.lokes.covidvacs

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.servlet.http.HttpServletResponse

@Controller
class DataController(private val dataProvider: Data) {


    @GetMapping("/")
    fun index() = "data"


    @GetMapping("/data")
    @ResponseBody
    fun data(response: HttpServletResponse) {
        val data = dataProvider.getData().joinToString("\n")
        val currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"))
        response.contentType = "text/csv"
        response.addHeader("Content-Disposition", "attachment; filename=pa_counties_covid_vaccine_count_${currentDate}.csv")
        response.writer.write(data)
    }
}
