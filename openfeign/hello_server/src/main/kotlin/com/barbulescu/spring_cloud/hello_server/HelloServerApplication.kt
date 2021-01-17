package com.barbulescu.spring_cloud.hello_server

import org.apache.logging.log4j.LogManager
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class HelloServerApplication

fun main(args: Array<String>) {
    runApplication<HelloServerApplication>(*args)
}

@Configuration
class SecurityConfig : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity?) {
        http?.httpBasic()
        http?.authorizeRequests()
            ?.anyRequest()?.authenticated()
    }

    @Bean
    fun defaultUserDetailsService(): UserDetailsService {
        val userDetailsService = InMemoryUserDetailsManager()
        val user = User.withUsername("u")
            .password("p")
            .authorities("read")
            .build()
        userDetailsService.createUser(user)
        return userDetailsService
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder? {
        return NoOpPasswordEncoder.getInstance()
    }
}

@RestController
class HelloController() {
    private val logger = LogManager.getLogger(javaClass)

    @GetMapping("/hello/{name}")
    fun sayHello(@PathVariable name: String): String {
        logger.info("Saying hello for $name")
//        Thread.sleep(10_000)
        logger.info("Done")
        return "Hello $name!"
    }
}