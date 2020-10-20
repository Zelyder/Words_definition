package com.zelyder.wordsdefinition.domain.models

data class ResultProperty(
    val definition: String,
    val partOfSpeech: String,
    val synonyms: List<String>,
    val typeOf: List<String>,
    val hasTypes: List<String>,
    val derivation: List<String>,
    val examples: List<String>,

)