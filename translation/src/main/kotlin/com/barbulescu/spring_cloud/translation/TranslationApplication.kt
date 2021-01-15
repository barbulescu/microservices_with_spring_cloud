package com.barbulescu.spring_cloud.translation

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.sleuth.Tracer
import org.springframework.cloud.sleuth.autoconfig.instrument.web.SleuthWebProperties
import org.springframework.cloud.sleuth.http.HttpRequestParser
import org.springframework.cloud.sleuth.instrument.web.HttpServerRequestParser
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.filter.GenericFilterBean
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@SpringBootApplication
@EnableWebMvc
class TranslationApplication

fun main(args: Array<String>) {
	runApplication<TranslationApplication>(*args)
}

@Component
@Order(SleuthWebProperties.TRACING_FILTER_ORDER + 1)
class MyFilter(val tracer: Tracer) : GenericFilterBean() {

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val res = response as HttpServletResponse
        val req = request as HttpServletRequest
        val currentSpan = tracer.currentSpan()
//        val inputKey = req.getHeader("input-key")
//        currentSpan?.tag("input-key", inputKey)
//        res.setHeader("Trace-Id", currentSpan?.context()?.traceId())
//        res.setHeader("input-key", inputKey)
		req.headerNames.asIterator().forEach {
			println ("$it=${req.getHeader(it)}")
		}
        chain?.doFilter(request, response)
    }
}
