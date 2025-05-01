package com.example.support.feature.phrasalverbs.presentation.data

import java.util.UUID

data class PhrasalVerbsGame(
    val id: String = UUID.randomUUID().toString(),
    val text: String = "",
    val answer: String = ""
)