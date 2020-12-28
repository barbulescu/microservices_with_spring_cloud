package com.barbulescu.spring_cloud.entry

import org.slf4j.LoggerFactory
import org.springframework.jms.core.JmsTemplate
import org.springframework.jms.support.converter.SimpleMessageConverter
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import javax.jms.Message

@RestController
class HelloController(
    private val translator: Translator,
    private val jmsTemplate: JmsTemplate
) {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val simpleMessageConverter = SimpleMessageConverter()

    @GetMapping("/hello/{name}")
    fun sayHello(
        @PathVariable name: String,
        @RequestHeader("special-key") specialKey: String
    ): String {
        logger.info("Saying hello for $name with key $specialKey")
        return "${translator.helloInGerman().capitalize()} $name!"
    }

    @GetMapping("/hello_jms/{name}")
    fun sayHelloJms(
        @PathVariable name: String,
        @RequestHeader("special-key") specialKey: String
    ): String {
        logger.info("Saying hello_jms for $name with key $specialKey")
        jmsTemplate.send("hello-request") {
            it.createTextMessage(name)
        }
        jmsTemplate.receiveTimeout = 1000
        val response: Message? = jmsTemplate.receive("hello-response")
        return if (response != null) simpleMessageConverter.fromMessage(response).toString() else "No response"
    }
}