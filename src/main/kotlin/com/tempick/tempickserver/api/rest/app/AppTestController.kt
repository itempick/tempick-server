package com.tempick.tempickserver.api.rest.app

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AppTestController {

    @GetMapping("/app/test")
    fun test(): String = "Hello App World!"
}