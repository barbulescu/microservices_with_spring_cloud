package com.barbulescu.spring_cloud.unmanaged

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@SpringBootApplication
@EnableWebMvc
class TranslationApplication

fun main(args: Array<String>) {
	runApplication<TranslationApplication>(*args)
}
