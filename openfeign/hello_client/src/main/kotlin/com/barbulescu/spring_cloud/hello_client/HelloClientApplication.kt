package com.barbulescu.spring_cloud.hello_client

import org.apache.logging.log4j.LogManager
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.context.annotation.Bean
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@SpringBootApplication
class HelloClientApplication

fun main(args: Array<String>) {
    runApplication<HelloClientApplication>(*args)
}

@SpringBootConfiguration
@EnableFeignClients
class MainConfig(val helloService: HelloService) {
    private val logger = LogManager.getLogger(javaClass)

    @Bean
    fun runner(): ApplicationRunner = ApplicationRunner {
        val name = "World"
        val response = helloService.sayHello(name)
        logger.info("sayHello($name) -> $response")
    }
}

@FeignClient(name="HelloService", url = "\${hello.url}")
interface HelloService {
    @RequestMapping(method = [RequestMethod.GET], value = ["/hello/{name}"])
    fun sayHello(@PathVariable("name") name: String): String

}