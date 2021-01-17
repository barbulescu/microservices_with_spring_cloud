package com.barbulescu.spring_cloud.hello_client

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class HelloClientApplication

fun main(args: Array<String>) {
    runApplication<HelloClientApplication>(*args)
}