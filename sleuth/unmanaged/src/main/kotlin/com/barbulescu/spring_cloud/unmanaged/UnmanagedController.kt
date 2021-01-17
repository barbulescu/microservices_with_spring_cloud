package com.barbulescu.spring_cloud.unmanaged

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UnmanagedController {

    @GetMapping("/check")
    fun sayHello(): Boolean {
        return true;
    }
}