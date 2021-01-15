package com.barbulescu.spring_cloud.hellomq

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.sleuth.Tracer
import org.springframework.cloud.sleuth.annotation.NewSpan
import org.springframework.jms.annotation.EnableJms
import org.springframework.jms.annotation.JmsListener
import org.springframework.jms.core.JmsTemplate
import org.springframework.stereotype.Component

@SpringBootApplication
@EnableJms
class HelloMQApplication(val jmsTemplate: JmsTemplate, val processor: SpecialProcessor, val tracer: Tracer) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @JmsListener(destination = "hello-request")
    fun sayHello(name: String) {
        commonSayHello(name, "hello-response")
    }

    @JmsListener(destination = "hello-request2")
    fun sayHello2(name: String) {
        commonSayHello(name, "hello-response2")
    }

    private fun commonSayHello(name: String, queue: String) {
        val traceId = tracer.currentSpan()?.context()?.traceId()
        logger.info("Saying: $name with trace $traceId")

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

