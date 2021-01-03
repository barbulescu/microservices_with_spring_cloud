package com.barbulescu.spring_cloud.hellomq

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.sleuth.annotation.NewSpan
import org.springframework.cloud.sleuth.brave.bridge.BraveTracer
import org.springframework.jms.annotation.EnableJms
import org.springframework.jms.annotation.JmsListener
import org.springframework.jms.core.JmsTemplate
import org.springframework.stereotype.Component

@SpringBootApplication
@EnableJms
class HelloMQApplication(val jmsTemplate: JmsTemplate, val processor: SpecialProcessor) {

    @JmsListener(destination = "hello-request")
    fun sayHello(name: String) {
        println("Saying: $name")

        processor.processSomething()

        jmsTemplate.send("hello-response") {
    		it.createTextMessage("Hello $name!")
		}
    }

}

@Component
class SpecialProcessor {
    @NewSpan("custom_span")
    fun processSomething() {
        println("processing something")
    }

}

fun main(args: Array<String>) {
    runApplication<HelloMQApplication>(*args)
}

