package com.example.pockotlin.exception

import java.lang.RuntimeException

data class ConflictException(val sessionId: String?) : RuntimeException()