package com.example.support.feature.seemore.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.support.R
import com.example.support.core.ui.AppTheme
import com.example.support.core.ui.AppTheme.colors
import com.example.support.core.ui.views.CustomBackgroundContainer
import com.example.support.core.domain.GameModel
import com.example.support.feature.seemore.model.SeeMoreState
import com.example.support.feature.seemore.presentation.viewModel.SeeMoreController

@Composable
fun SeeMoreContentView(
    modifier: Modifier = Modifier,
    controller: SeeMoreController,
    state: SeeMoreState,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        colors.backgroundGradientFirst.copy(0.6f),
                        colors.backgroundGradientSecond
                    )
                )
            ),
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
        ) {
            CustomBackgroundContainer(
                modifierWhole = modifier
                    .fillMaxHeight(0.1f)
                    .clip(RoundedCornerShape(
                        50.dp
                    )),
                modifierContent = modifier.padding(8.dp),

                ) {
                HeaderContent(controller = controller)
            }
            Spacer(modifier = Modifier.height(16.dp))

            GamesList(
                state = state,
                controller = controller
            )
        }
    }
}

@Composable
private fun HeaderContent(
    controller: SeeMoreController
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    ) {
        OutlinedButton(
            onClick = { controller.onNavigateBack() },
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = AppTheme.colors.primary
            ),
            border = ButtonDefaults.outlinedButtonBorder.copy(
                brush = Brush.horizontalGradient(
                    listOf(Color.White.copy(alpha = 0.3f), Color.White.copy(alpha = 0.3f))
                )
            ),
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_circle_right),
                contentDescription = "See More",
                modifier = Modifier.rotate(180f)
            )
        }

        Text(
            text = stringResource(R.string.all_games),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}


@Composable
private fun GamesList(
    state: SeeMoreState,
    controller: SeeMoreController
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(state.games.chunked(2)) { rowGames ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                rowGames.forEach { gameItem ->
                    SeeMoreCardView(
                        nameGame = gameItem.title,
                        gameDescription = gameItem.description,
                        onClick = {
                            controller.onNavigateGame(route = gameItem.route)
                        }
                    )
                }
            }
        }
    }
}

private val mockGames = listOf(
    GameModel(
        id = "1",
        title = "Phrasal Verb",
        description = "Fill in the blanks with the correct phrasal verb to complete the sentence.",
        imgResource = R.drawable.img_game_person,
        route = com.example.support.core.navigation.model.NavigationItem.FactOpinion.route
    ),
    GameModel(
        id = "2",
        title = "Fact or Opinion",
        description = "Read each statement and determine whether it presents a fact or an opinion.",
        imgResource = R.drawable.img_game_person2,
        route = com.example.support.core.navigation.model.NavigationItem.PhrasalVerbs.route
    ),
    GameModel(
        id = "3",
        title = "Synonyms",
        description = "Catch the synonyms for a given word based on a range of topics.",
        imgResource = R.drawable.img_game_person2,
        route = com.example.support.core.navigation.model.NavigationItem.KeywordsCheck.route
    ),
    GameModel(
        id = "4",
        title = "Essay Builder",
        description = "Choose the correct words to form a coherent and well-structured IELTS essay.",
        imgResource = R.drawable.img_game_person2,
        route = com.example.support.core.navigation.model.NavigationItem.KeywordsCheck.route
    ),
    GameModel(
        id = "5",
        title = "Choose Keywords",
        description = "Select the most relevant keywords from a set of options based on real IELTS reading tasks.",
        imgResource = R.drawable.img_game_person2,
        route = com.example.support.core.navigation.model.NavigationItem.KeywordsCheck.route
    )
)

@Preview
@Composable
private fun SeeMoreContentViewPreview() {
    val mockController = object : SeeMoreController {
        override fun loadGames() {
            TODO("Not yet implemented")
        }

        override fun onNavigateBack() {
            TODO("Not yet implemented")
        }

        override fun onNavigateGame(route: String) {
            TODO("Not yet implemented")
        }
    }

    AppTheme {
        SeeMoreScreen(
            state = SeeMoreState(
                games = mockGames
            ),
            controller = mockController
        )
    }
}