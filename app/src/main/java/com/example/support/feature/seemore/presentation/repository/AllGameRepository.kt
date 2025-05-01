package com.example.support.feature.seemore.presentation.repository

import com.example.support.R
import com.example.support.core.navigation.model.NavigationItem
import com.example.support.core.domain.GameModel
import kotlinx.coroutines.delay
import javax.inject.Inject

class AllGameRepository @Inject constructor() {

    suspend fun loadGames(): Result<List<GameModel>> {
        delay(500)

        return Result.success(
            listOf(
                GameModel(
                    id = "1",
                    title = "Phrasal Verb",
                    description = "Fill in the blanks with the correct phrasal verb to complete the sentence.",
                    imgResource = R.drawable.img_game_person,
                    route = NavigationItem.PhrasalVerbs.route
                ),
                GameModel(
                    id = "2",
                    title = "Fact or Opinion",
                    description = "Read each statement and determine whether it presents a fact or an opinion.",
                    imgResource = R.drawable.img_game_person2,
                    route = NavigationItem.FactOpinion.route
                ),
                GameModel(
                    id = "3",
                    title = "Synonyms",
                    description = "Catch the synonyms for a given word based on a range of topics.",
                    imgResource = R.drawable.img_game_person2,
                    route = NavigationItem.Synonyms.route
                ),
                GameModel(
                    id = "4",
                    title = "Essay Builder",
                    description = "Choose the correct words to form a coherent and well-structured IELTS essay.",
                    imgResource = R.drawable.img_game_person2,
                    route = NavigationItem.EssayBuilder.route
                ),
                GameModel(
                    id = "5",
                    title = "Choose Keywords",
                    description = "Select the most relevant keywords from a set of options based on real IELTS reading tasks.",
                    imgResource = R.drawable.img_game_person2,
                    route = NavigationItem.KeywordsCheck.route
                )
            )
        )
    }
}
