package com.example.support.feature.synonyms.presentation.viewModel

import com.example.support.feature.synonyms.model.SynonymsEvent

interface SynonymsController {
    fun onEvent(event: SynonymsEvent)
    fun toggleSelection(index: Int)
}