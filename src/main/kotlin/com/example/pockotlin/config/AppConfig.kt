package com.example.pockotlin.config

import org.springframework.core.task.SimpleAsyncTaskExecutor
import org.springframework.core.task.TaskExecutor

fun taskExecutor(): TaskExecutor = SimpleAsyncTaskExecutor()
