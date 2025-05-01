package com.example.support.feature.gamecompletion.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.support.R
import com.example.support.core.ui.AppTheme
import com.example.support.core.ui.AppTheme.colors
import com.example.support.core.ui.components.animations.CongratulationsView
import com.example.support.core.ui.components.button.SubmitButton
import com.example.support.core.ui.components.text.HeaderGameText
import com.example.support.feature.gamecompletion.model.GameCompletionEvent
import com.example.support.feature.gamecompletion.model.GameCompletionState
import com.example.support.feature.gamecompletion.presentation.viewModel.GameCompletionController

@Composable
fun GameCompletionContentView(
    modifier: Modifier = Modifier,
    state: GameCompletionState,
    controller: GameCompletionController
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        colors.backgroundGradientFirst,
                        colors.backgroundGradientSecond
                    )
                )
            )
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                modifier = modifier
                .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                CongratulationsView(
                    modifier = modifier
                        .height(200.dp)
                )
                HeaderGameText(strResource = R.string.game_complete)
                HorizontalDivider(modifier = Modifier
                    .fillMaxWidth()
                )
                GameCompletionCard(
                    state = state
                )
            }
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SubmitButton(
                    strResource = R.string.new_game,
                    textColorRes =  colors.primaryVariant,
                    buttonColorRes = colors.primary,
                    onClick = {
                        controller.onEvent(GameCompletionEvent.NewGame)
                    }
                )
                SubmitButton(
                    strResource = R.string.quit,
                    textColorRes =  colors.primary,
                    buttonColorRes = colors.secondary,
                    onClick = {
                        controller.onEvent(GameCompletionEvent.QuitGame)

                    }
                )
            }


        }
    }
}


@Preview
@Composable
private fun GameCompletionContentViewPreview() {
    val mockController = object: GameCompletionController {
        override fun onEvent(event: GameCompletionEvent) {
            TODO("Not yet implemented")
        }
    }

    AppTheme {
        GameCompletionContentView(
            state = GameCompletionState(),
            controller = mockController
        )
    }
}