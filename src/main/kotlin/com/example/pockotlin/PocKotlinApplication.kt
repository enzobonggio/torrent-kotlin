package com.example.pockotlin

import com.fasterxml.jackson.databind.Module
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class PocKotlinApplication

fun main(args: Array<String>) {
    runApplication<PocKotlinApplication>(*args)
}
