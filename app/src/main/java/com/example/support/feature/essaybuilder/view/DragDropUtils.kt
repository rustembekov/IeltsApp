package com.example.support.feature.essaybuilder.view

import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.foundation.gestures.detectTapGestures

private var draggedWord: String? = null

fun Modifier.dragSource(
    word: String
): Modifier = this.pointerInput(word) {
    detectTapGestures(
        onPress = {
            draggedWord = word
            tryAwaitRelease()
            draggedWord = null
        }
    )
}

fun Modifier.dropTarget(
    onDrop: (String) -> Unit
): Modifier = this.pointerInput(Unit) {
    detectTapGestures(
        onTap = {
            draggedWord?.let { word ->
                onDrop(word)
            }
        }
    )
}
