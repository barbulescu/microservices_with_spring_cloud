package com.barbulescu.spring_cloud.hello_client.config

import org.apache.logging.log4j.LogManager
import org.springframework.boot.ApplicationRunner
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Configuration
class DefaultConfiguration(val helloService: HelloService) {
    private val logger = LogManager.getLogger(javaClass)

    @Bean
    fun defaultRunner(): ApplicationRunner = ApplicationRunner {
//        val name = "World"
//        val response = helloService.sayHello(name)
//        logger.info("sayHello($name) -> $response")
    }
}

@FeignClient(name = "HelloService", url = "\${hello.url}")
interface HelloService {
    @RequestMapping(method = [RequestMethod.GET], value = ["/hello/{name}"])
    fun sayHello(@PathVariable("name") name: String): String
}
