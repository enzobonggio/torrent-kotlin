package com.example.pockotlin.dto

import am.ik.yavi.builder.ValidatorBuilder
import am.ik.yavi.builder.constraint
import am.ik.yavi.core.ConstraintViolations
import am.ik.yavi.fn.Either

data class AddTorrentDTO(val url: String) {
    companion object {
        val validator = ValidatorBuilder.of<AddTorrentDTO>()
                .constraint(AddTorrentDTO::url) { notNull() }
                .build()
    }

    fun validate(): Either<ConstraintViolations, AddTorrentDTO> {
        return validator.validateToEither(this)
    }
}