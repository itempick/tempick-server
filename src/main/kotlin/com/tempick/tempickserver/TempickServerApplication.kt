package com.tempick.tempickserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TempickServerApplication

fun main(args: Array<String>) {
    runApplication<TempickServerApplication>(*args)
}
