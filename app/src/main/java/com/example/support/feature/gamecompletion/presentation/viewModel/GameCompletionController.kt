package com.example.support.feature.gamecompletion.presentation.viewModel

import com.example.support.feature.gamecompletion.model.GameCompletionEvent

interface GameCompletionController {
    fun onEvent(event: GameCompletionEvent)
}