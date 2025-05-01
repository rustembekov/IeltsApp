package com.example.support.feature.keywordscheck.presentation.viewModel

import com.example.support.feature.keywordscheck.model.KeywordsCheckEvent

interface KeywordsCheckController {
    fun onEvent(event: KeywordsCheckEvent)
    fun toggleWordSelection(word: String)
}