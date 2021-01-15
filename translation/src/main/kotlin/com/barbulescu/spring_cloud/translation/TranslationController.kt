package com.barbulescu.spring_cloud.translation

import org.slf4j.LoggerFactory
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class TranslationController(val checker: Checker) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping("/translate/{word}/{language}")
    fun sayHello(@PathVariable word: String, @PathVariable language: Language): Translation {
        logger.info("Called with key ${checker.check()}")
        return when (word) {
            "hello" -> when (language) {
                Language.DE -> Translation("hallo")
                Language.EN -> Translation("hello")
            }
            else -> Translation("unsupported")
        }
    }
}

@Component
class Checker(restTemplateBuilder: RestTemplateBuilder) {

    private val restTemplate = restTemplateBuilder
        .rootUri("http://localhost:8082")
        .build()

    fun check(): Boolean =
        restTemplate.getForEntity("/check", Boolean::class.java).body ?: false
}

enum class Language {
    DE, EN
}

data class Translation(val value: String)