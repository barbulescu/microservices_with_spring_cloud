package com.barbulescu.spring_cloud.translation

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.sleuth.http.HttpRequestParser
import org.springframework.cloud.sleuth.instrument.web.HttpServerRequestParser
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import javax.servlet.http.HttpServletRequest

@SpringBootApplication
@EnableWebMvc
class TranslationApplication

fun main(args: Array<String>) {
	runApplication<TranslationApplication>(*args)
}

@Configuration
class MainConfig {

    @Bean(name = [HttpServerRequestParser.NAME])
    fun myHttpRequestParser(): HttpRequestParser? {
        return HttpRequestParser { request, _, span ->
            val req: Any = request.unwrap()
            if (req is HttpServletRequest) {
                span.tag("special-key", req.getHeader("special-key"))
            }
        }
    }
}