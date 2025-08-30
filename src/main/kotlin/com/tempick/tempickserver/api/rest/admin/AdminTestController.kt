package com.tempick.tempickserver.api.rest.admin

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AdminTestController {
    @GetMapping("/admin/test")
    fun test(): String = "Hello Admin World!"
}