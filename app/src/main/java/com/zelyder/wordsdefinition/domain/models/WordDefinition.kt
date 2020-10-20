package com.zelyder.wordsdefinition.domain.models

import com.squareup.moshi.Json

data class WordDefinition(
    @Json(name = "word")
    val word: String,
    @Json(name = "definitions")
    val definitions: List<Definition>
)