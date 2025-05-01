package com.example.support.feature.phrasalverbs.presentation.viewModel

import com.example.support.feature.phrasalverbs.model.PhrasalVerbsEventEvent

interface PhrasalVerbsController {
    fun onEvent(event: PhrasalVerbsEventEvent)
    fun onInputChange(input: String)
}