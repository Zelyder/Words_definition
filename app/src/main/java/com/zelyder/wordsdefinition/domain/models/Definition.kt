package com.zelyder.wordsdefinition.domain.models

import com.squareup.moshi.Json


data class Definition(
    @Json(name = "definition")
    val definition: String,
    @Json(name = "partOfSpeech")
    val partOfSpeech: String,
    @Transient
    val id: Long = 0
)