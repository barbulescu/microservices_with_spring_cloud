package com.barbulescu.spring_cloud.entry

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.cloud.sleuth.Tracer
import org.springframework.cloud.sleuth.autoconfig.instrument.web.SleuthWebProperties.TRACING_FILTER_ORDER
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.jms.annotation.EnableJms
import org.springframework.stereotype.Component
import org.springframework.web.filter.GenericFilterBean
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletResponse

@SpringBootApplication
class EntryApplication

fun main(args: Array<String>) {
    runApplication<EntryApplication>(*args)
}

@Configuration
@EnableJms
@EnableWebMvc
@EnableFeignClients
class MainConfig

@Component
@Order(TRACING_FILTER_ORDER + 1)
class MyFilter(val tracer: Tracer) : GenericFilterBean() {

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val res = response as HttpServletResponse
        res.setHeader("Trace-Id", tracer.currentSpan()?.context()?.traceId())
        chain?.doFilter(request, response)
    }
}
