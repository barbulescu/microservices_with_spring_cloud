package com.barbulescu.spring_cloud.entry

import org.slf4j.LoggerFactory
import org.springframework.cloud.sleuth.Tracer
import org.springframework.jms.annotation.JmsListener
import org.springframework.jms.core.JmsTemplate
import org.springframework.jms.support.converter.SimpleMessageConverter
import org.springframework.stereotype.Component
import java.util.concurrent.Exchanger
import java.util.concurrent.TimeUnit.SECONDS
import javax.jms.Message

@Component
class JmsClient(val jmsTemplate: JmsTemplate, val tracer: Tracer) {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val simpleMessageConverter = SimpleMessageConverter()
    private val exchanger = Exchanger<String>()

    @JmsListener(destination = "hello-response2")
    fun responseListener(message: Message) {
        val response = simpleMessageConverter.fromMessage(message).toString()
        exchanger.exchange(response, 2, SECONDS)
    }

    fun sayHello(name: String, specialKey: String): String {
        sendRequest(name, specialKey, "hello-request")
        jmsTemplate.receiveTimeout = 1000
        val response: Message? = jmsTemplate.receive("hello-response")
        return if (response != null) simpleMessageConverter.fromMessage(response).toString() else "No response"
    }

    fun sayHelloWithListener(name: String, specialKey: String): String {
        sendRequest(name, specialKey, "hello-request2")
        return exchanger.exchange("-", 2, SECONDS)
    }

    private fun sendRequest(name: String, specialKey: String, queue: String) {
        logger.info("Saying hello_jms for $name with key $specialKey")
        jmsTemplate.send(queue) {
            it.createTextMessage(name)
        }
    }
}