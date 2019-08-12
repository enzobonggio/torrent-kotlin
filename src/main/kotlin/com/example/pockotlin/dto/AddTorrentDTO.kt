package com.example.pockotlin.dto

import javax.validation.constraints.NotNull

data class AddTorrentDTO(@NotNull val url: String)