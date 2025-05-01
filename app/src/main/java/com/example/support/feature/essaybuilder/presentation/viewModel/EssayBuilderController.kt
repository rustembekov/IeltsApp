package com.example.support.feature.essaybuilder.presentation.viewModel

import com.example.support.feature.essaybuilder.model.EssayBuilderEvent

interface EssayBuilderController {
    fun onEvent(event: EssayBuilderEvent)
    fun onWordClick(word: String)
    fun onBlankClick(index: Int)
}