package com.barbulescu.spring_cloud.translation

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class TranslationController {

    @GetMapping("/translate/{word}/{language}")
    fun sayHello(@PathVariable word:String, @PathVariable language: Language): Translation =
        when(word) {
            "hello" -> when(language) {
                Language.DE -> Translation("hallo")
                Language.EN -> Translation("hello")
            }
            else -> Translation("unsupported")
        }
}

enum class Language {
    DE, EN
}

data class Translation(val value:String)