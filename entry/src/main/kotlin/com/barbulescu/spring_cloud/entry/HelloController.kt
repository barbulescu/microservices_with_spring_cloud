package com.barbulescu.spring_cloud.entry

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController(private val translator: Translator) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping("/hello/{name}")
    fun sayHello(
        @PathVariable name: String,
        @RequestHeader("special-key") specialKey: String
    ): String {
        logger.info("Saying hello for $name with key $specialKey")
        return "${translator.helloInGerman().capitalize()} $name!"
    }
}