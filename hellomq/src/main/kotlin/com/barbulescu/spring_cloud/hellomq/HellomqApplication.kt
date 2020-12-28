package com.barbulescu.spring_cloud.hellomq

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.jms.annotation.EnableJms
import org.springframework.jms.annotation.JmsListener
import org.springframework.jms.core.JmsTemplate

@SpringBootApplication
@EnableJms
class HellomqApplication(private val jmsTemplate: JmsTemplate) {

    @JmsListener(destination = "hello-request")
    fun sayHello(name: String) {
        println("Saying: $name")
    	jmsTemplate.send("hello-response") {
    		it.createTextMessage("Hello $name!")
		}
    }

}

fun main(args: Array<String>) {
    runApplication<HellomqApplication>(*args)
}

