package com.barbulescu.spring_cloud.entry

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EntryApplication

fun main(args: Array<String>) {
	runApplication<EntryApplication>(*args)
}
