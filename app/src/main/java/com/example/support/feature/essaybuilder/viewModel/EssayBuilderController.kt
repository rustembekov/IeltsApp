package com.example.support.feature.essaybuilder.viewModel

import com.example.support.feature.essaybuilder.model.EssayBuilderEvent

interface EssayBuilderController {
    fun onEvent(event: EssayBuilderEvent)
    fun updateBlanks(blanks: List<String?>)

}