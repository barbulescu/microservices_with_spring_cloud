package com.barbulescu.spring_cloud.entry

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController(private val translator: Translator) {

    @GetMapping("/hello/{name}")
    fun sayHello(@PathVariable name: String): String = "${translator.helloInGerman().capitalize()} $name!"
}