package com.example.support.core.ui.views.pauseDialog.model

interface PauseState {
    val isPauseDialogVisible: Boolean

    fun copyPauseState(isPauseDialogVisible: Boolean): PauseState
}


interface PauseController {
    fun onPauseClicked()
    fun onDismissPauseDialog()
}
