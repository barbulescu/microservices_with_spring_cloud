package com.barbulescu.spring_cloud.translation

import org.springframework.cloud.sleuth.http.HttpRequestParser
import org.springframework.cloud.sleuth.instrument.web.HttpServerRequestParser
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.servlet.http.HttpServletRequest

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