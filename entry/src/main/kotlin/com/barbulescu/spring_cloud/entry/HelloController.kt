package com.barbulescu.spring_cloud.entry

import org.slf4j.LoggerFactory
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.*


@RestController
class HelloController(val translator: Translator, val jmsClient: JmsClient) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping("/hello/{name}")
    fun sayHello(@PathVariable name: String, @RequestHeader("input-key") specialKey: String): String {
        logger.info("Saying hello for $name with key $specialKey")
        val helloResponse = sayHello(Language.DE)
        val capitalizedResponse = helloResponse.capitalize()
        return "$capitalizedResponse $name!"
    }

    private fun sayHello(language: Language): String =
        translator.translate(language)?.value ?: "Unknown translation"

    @GetMapping("/hello_jms/{name}")
    fun sayHelloJms(@PathVariable name: String, @RequestHeader("input-key") specialKey: String): String {
        return jmsClient.sayHello(name, specialKey)
    }

    @GetMapping("/hello_jms_listener/{name}")
    fun sayHelloJmsWithListener(@PathVariable name: String, @RequestHeader("input-key") specialKey: String): String {
        return jmsClient.sayHelloWithListener(name, specialKey)
    }
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