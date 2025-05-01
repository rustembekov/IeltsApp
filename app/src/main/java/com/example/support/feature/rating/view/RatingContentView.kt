package com.example.support.feature.rating.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.support.core.ui.AppTheme
import com.example.support.core.ui.AppTheme.colors
import com.example.support.core.ui.views.CustomBackgroundContainer
import com.example.support.core.domain.User
import com.example.support.feature.rating.model.RatingState
import com.example.support.feature.rating.presentation.viewModel.RatingController

@Composable
fun RatingContentView(
    modifier: Modifier = Modifier,
    state: RatingState,
    controller: RatingController
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        colors.backgroundGradientFirst.copy(alpha = 0.5f),
                        colors.backgroundGradientSecond
                    )
                )
            ),
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()

        ) {

            TopPodiumContent()

            ListPlayers(state = state)
        }
    }
}

@Composable
private fun TopPodiumContent() {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        CustomBackgroundContainer(
            modifierWhole = Modifier
                .fillMaxHeight(0.4f)
                .clip(
                    RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomStart = 50.dp,
                        bottomEnd = 50.dp
                    )
                )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {
                PodiumItem(rank = 2, height = 100.dp)
                PodiumItem(rank = 1, height = 140.dp)
                PodiumItem(rank = 3, height = 80.dp)
            }
        }
    }
}

@Composable
private fun ListPlayers(state: RatingState) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        state.players
            .filter { it.rank > 3 }
            .let { remainingPlayers ->
                items(remainingPlayers) { player ->
                    PlayerRow(player)
                }
            }
    }
}

@Preview
@Composable
private fun RatingContentViewPreview() {
    val mockPlayers = listOf(
        User(
            id = "1",
            email = "john.doe@example.com",
            username = "John Doe",
            password = "password123",
            score = 100,
            rank = 1
        ),
        User(
            id = "2",
            email = "jane.doe@example.com",
            username = "Jane Doe",
            password = "password123",
            score = 90,
            rank = 2
        ),
        User(
            id = "3",
            email = "bob.smith@example.com",
            username = "Bob Smith",
            password = "password123",
            score = 80,
            rank = 3
        ),
        User(
            id = "4",
            email = "alice.johnson@example.com",
            username = "Alice Johnson",
            password = "password123",
            score = 70,
            rank = 4
        ),
        User(
            id = "5",
            email = "alice.johnson@example.com",
            username = "Alice Johnson",
            password = "password123",
            score = 140,
            rank = 5
        )

    )

    val mockState = RatingState(players = mockPlayers)

    val mockController = object : RatingController {
        override fun loadUsers() {}
    }

    AppTheme {
        RatingContentView(
            modifier = Modifier,
            state = mockState,
            controller = mockController
        )
    }
}
