package com.example.support.feature.factopinion.presentation.data

import java.util.UUID

data class FactOpinionGame(
    val id: String = UUID.randomUUID().toString(),
    val text: String = "",
    val answer: String = ""
)