package com.barbulescu.spring_cloud.hello_client.config

import feign.Feign
import feign.Param
import feign.RequestLine
import feign.auth.BasicAuthRequestInterceptor
import org.apache.logging.log4j.LogManager
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ManualConfiguration(@Value("\${hello.url}") val baseUrl: String) {
    private val logger = LogManager.getLogger(javaClass)

    @Bean
    fun manualRunner(): ApplicationRunner = ApplicationRunner {
        val name = "World"
        val response = manualHelloService().sayHello(name)
        logger.info("sayHelloWithManualInstance($name) -> $response")
    }

    @Bean
    fun manualHelloService(): HelloServiceManual {
        return Feign.builder()
            .requestInterceptor(BasicAuthRequestInterceptor("u", "p"))
            .target(HelloServiceManual::class.java, baseUrl)
    }

}

interface HelloServiceManual {
    @RequestLine("GET /hello/{name}")
    fun sayHello(@Param("name") name: String): String
}