package com.barbulescu.spring_cloud.hellomq

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.sleuth.annotation.NewSpan
import org.springframework.jms.annotation.EnableJms
import org.springframework.jms.annotation.JmsListener
import org.springframework.jms.core.JmsTemplate
import org.springframework.stereotype.Component

@SpringBootApplication
@EnableJms
class HelloMQApplication(val jmsTemplate: JmsTemplate, val processor: SpecialProcessor) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @JmsListener(destination = "hello-request")
    fun sayHello(name: String) {
        s(name, "hello-response")
    }

    @JmsListener(destination = "hello-request2")
    fun sayHello2(name: String) {
        s(name, "hello-response2")
    }

    private fun s(name: String, queue:String) {
        logger.info("Saying: $name")

        processor.processSomething()

        jmsTemplate.send(queue) {
            it.createTextMessage("Hello $name!")
        }
    }

}

@Component
class SpecialProcessor {
    private val logger = LoggerFactory.getLogger(javaClass)

    @NewSpan("custom_span")
    fun processSomething() {
        logger.info("processing something")
    }

}

fun main(args: Array<String>) {
    runApplication<HelloMQApplication>(*args)
}

