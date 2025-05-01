package com.example.support.feature.factopinion.presentation.viewModel

import com.example.support.feature.factopinion.model.FactOpinionEvent

interface FactOpinionController {
    fun onEvent(event: FactOpinionEvent)
    fun selectedAnswer(buttonText: String)
}