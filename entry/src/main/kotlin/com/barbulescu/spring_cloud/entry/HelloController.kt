package com.barbulescu.spring_cloud.entry

import org.slf4j.LoggerFactory
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.jms.core.JmsTemplate
import org.springframework.jms.support.converter.SimpleMessageConverter
import org.springframework.web.bind.annotation.*
import javax.jms.Message


@RestController
class HelloController(private val translator: Translator, private val jmsTemplate: JmsTemplate) {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val simpleMessageConverter = SimpleMessageConverter()

    @GetMapping("/hello/{name}")
    fun sayHello(@PathVariable name: String, @RequestHeader("special-key") specialKey: String): String {
        logger.info("Saying hello for $name with key $specialKey")
        return "${sayHello(Language.DE).capitalize()} $name!"
    }

    @GetMapping("/hello_jms/{name}")
    fun sayHelloJms(@PathVariable name: String, @RequestHeader("special-key") specialKey: String): String {
        logger.info("Saying hello_jms for $name with key $specialKey")
        jmsTemplate.send("hello-request") {
            it.createTextMessage(name)
        }
        jmsTemplate.receiveTimeout = 1000
        val response: Message? = jmsTemplate.receive("hello-response")
        return if (response != null) simpleMessageConverter.fromMessage(response).toString() else "No response"
    }

    private fun sayHello(language: Language): String =
        translator.translate(language)?.value ?: "Unknown translation"

}


@FeignClient("translator", url = "\${translator.url}")
interface Translator {

    @RequestMapping(method = [RequestMethod.GET], value = ["/translate/hello/{language}"])
    fun translate(@PathVariable("language") language: Language): Translation?

}

enum class Language {
    EN, DE
}

data class Translation(val value: String)