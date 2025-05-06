package com.example.support.feature.home.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.support.R
import com.example.support.core.navigation.model.NavigationItem
import com.example.support.core.ui.AppTheme
import com.example.support.core.ui.components.StatsCard
import com.example.support.core.domain.GameModel
import com.example.support.core.domain.User
import com.example.support.feature.home.model.HomeEvent
import com.example.support.core.ui.AppTheme.colors as ColorApp
import com.example.support.feature.home.model.HomeUiState
import com.example.support.feature.home.presentation.viewModel.HomeController

@Composable
fun HomeContentView(
    modifier: Modifier = Modifier,
    state: HomeUiState.Content,
    controller: HomeController
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        ColorApp.backgroundGradientFirst,
                        ColorApp.backgroundGradientSecond
                    )
                )
            ),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
//            HeaderText()
            HomeProfileCard(state = state, controller = controller)

            StatsCard(
                ranking = state.user?.rank.toString(),
                points = state.user?.score.toString()
            )

            GameSectionHeader(controller = controller)

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(state.games) { gameItem ->
                    HomeCardGameView(
                        nameGame = gameItem.title,
                        gameImgResource = gameItem.imgResource,
                        onClick = {
                            controller.onNavigateToRoute(route = gameItem.route)
                        },
                    )
                }
            }
        }
    }
}

@Composable
private fun HeaderText(
    modifier: Modifier = Modifier
) {
    val brush = Brush.horizontalGradient(
        colors = listOf(
            AppTheme.colors.backgroundGradientFirst,
            AppTheme.colors.backgroundGradientSecond.copy(alpha = 0.7f),
        )
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush,
                shape = RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 24.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 24.dp
                ))
    ) {
        Text(
            text = stringResource(R.string.inspiration),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}


@Composable
private fun GameSectionHeader(controller: HomeController) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.play_games),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary,
        )

        OutlinedButton(
            onClick = { controller.onNavigateToRoute(NavigationItem.SeeMore.route) },
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = AppTheme.colors.primary
            ),
            border = ButtonDefaults.outlinedButtonBorder.copy(
                brush = Brush.horizontalGradient(
                    listOf(Color.White.copy(alpha = 0.3f), Color.White.copy(alpha = 0.3f))
                )
            )
        ) {
            Text(
                text = stringResource(R.string.see_more),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_circle_right),
                contentDescription = "See More",
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }
}


private val mockGames = listOf(
    GameModel(
        id = "1",
        title = "Fact or Opinion",
        description = "Test your knowledge",
        imgResource = R.drawable.img_phrasal_verb,
        route = NavigationItem.FactOpinion.route
    ),
    GameModel(
        id = "2",
        title = "Phrasal Verb",
        description = "Challenge yourself",
        imgResource = R.drawable.img_fact_opinion,
        route = NavigationItem.PhrasalVerbs.route
    ),
    GameModel(
        id = "3",
        title = "Third Game",
        description = "Improve your skills",
        imgResource = R.drawable.img_phrasal_verb,
        route = NavigationItem.KeywordsCheck.route
    )
)

@Preview(showBackground = false)
@Composable
private fun HomeContentViewPreview() {
    AppTheme(
        darkTheme = false
    ) {
        val mockController = object : HomeController {
            override fun onNavigateToRoute(route: String) {}
            override fun onEvent(event: HomeEvent) {}
        }
        HomeContentView(
            controller = mockController,
            state = HomeUiState.Content(
                user = User(
                    username = "Irina",
                    email = "testing@gmail.com",
                    rank = 23,
                    score = 120
                ),
                games = mockGames,
                selectedImageUri = null
            )
        )
    }
}