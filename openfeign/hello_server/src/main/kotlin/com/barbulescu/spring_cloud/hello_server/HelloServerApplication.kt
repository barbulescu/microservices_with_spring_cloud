package com.barbulescu.spring_cloud.hello_server

import org.apache.logging.log4j.LogManager
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class HelloServerApplication

fun main(args: Array<String>) {
	runApplication<HelloServerApplication>(*args)
}

@RestController
class HelloController() {
    private val logger = LogManager.getLogger(javaClass)

    @GetMapping("/hello/{name}")
    fun sayHello(@PathVariable name: String): String {
        logger.info("Saying hello for $name")
        return "Hello $name!"
    }
}