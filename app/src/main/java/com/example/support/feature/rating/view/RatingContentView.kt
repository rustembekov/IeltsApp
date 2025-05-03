package com.example.support.feature.rating.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.support.R
import com.example.support.core.ui.AppTheme
import com.example.support.core.ui.AppTheme.colors
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
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)

        ) {

            PodiumList(
                state = state
            )

            ListPlayers(state = state)
        }
    }
}

@Composable
private fun PodiumList(state: RatingState) {
    val topThree = state.players.take(3).sortedBy { it.rank }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(50.dp))
            .background(
                Brush.linearGradient(
                    listOf(
                        colors.backgroundGradientFirst,
                        colors.backgroundGradientSecond.copy(alpha = 0.5f)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(R.string.leaderboard),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .padding(top = 48.dp) // push row below header
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {
                val heights = mapOf(1 to 88.dp, 2 to 64.dp, 3 to 64.dp)
                // 2nd, 1st, 3rd place order visually
                val order = listOf(2, 1, 3)

                order.mapNotNull { place ->
                    topThree.find { it.rank == place }?.let { user ->
                        PodiumItem(user = user, height = heights[place] ?: 60.dp, avatarUrl = state.avatars[user.id])
                    }
                }
            }
        }

        // Decorative background circles (optional)
        Box(
            modifier = Modifier
                .size(140.dp)
                .offset(x = (-10).dp, y = (-90).dp)
                .background(
                    color = colors.backgroundGradientFirst.copy(alpha = 0.3f),
                    shape = CircleShape
                )
                .align(Alignment.TopStart)
        )
        Box(
            modifier = Modifier
                .size(180.dp)
                .offset(x = 110.dp)
                .background(
                    color = colors.backgroundGradientFirst.copy(alpha = 0.3f),
                    shape = CircleShape
                )
                .align(Alignment.CenterEnd)
        )
    }
}


@Composable
private fun ListPlayers(state: RatingState) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val remainingPlayers = state.players.filter { it.rank > 3 }
//        for ((key, value) in state.avatars) {
//            Log.d("RatingViewModel", "Key: $key, Value: $value")
//        }

        items(remainingPlayers) { player ->
            Log.d("RatingViewModel", "${state.avatars[player.id]}")
            PlayerRow(player = player, avatarUrl = state.avatars[player.id])
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
