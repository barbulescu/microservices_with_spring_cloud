package com.barbulescu.spring_cloud.entry

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.sleuth.http.HttpRequestParser
import org.springframework.cloud.sleuth.instrument.web.HttpServerRequestParser
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jms.annotation.EnableJms
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import javax.servlet.http.HttpServletRequest

@SpringBootApplication
class EntryApplication

fun main(args: Array<String>) {
	runApplication<EntryApplication>(*args)
}

@Configuration
@EnableJms
@EnableWebMvc
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