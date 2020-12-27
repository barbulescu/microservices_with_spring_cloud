package com.barbulescu.spring_cloud.unmanaged

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Component

@Component
class Checker(restTemplateBuilder: RestTemplateBuilder) {

    private val restTemplate = restTemplateBuilder
        .rootUri("http://localhost:8082")
        .build()

    fun check(): Boolean =
        restTemplate.getForEntity("/check", Boolean::class.java).body ?: false
}