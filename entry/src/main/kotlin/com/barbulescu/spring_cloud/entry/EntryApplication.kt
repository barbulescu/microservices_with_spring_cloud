package com.barbulescu.spring_cloud.entry

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@SpringBootApplication
class EntryApplication

fun main(args: Array<String>) {
	runApplication<EntryApplication>(*args)
}
