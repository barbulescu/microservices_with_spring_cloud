package com.barbulescu.spring_cloud.entry

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.stereotype.Component

@Component
class Translator(restTemplateBuilder: RestTemplateBuilder) {

    private val restTemplate = restTemplateBuilder
        .rootUri("http://localhost:8081")
        .build()

    fun helloInEnglish(): String = helloIn("EN")
    fun helloInGerman(): String = helloIn("DE")
    private fun helloIn(language: String): String =
        restTemplate.getForEntity("/translate/hello/${language}", Translation::class.java).body?.value ?: "Unknown translation"
}

data class Translation(val value:String)