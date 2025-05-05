package com.example.support.core.navigation.model

import com.example.support.core.util.Constants


sealed class NavigationItem(val route: String) {
    data object Register : NavigationItem("register")
    
    data object Login : NavigationItem("login")
    
    data object FactOpinion : NavigationItem(Constants.FACT_OPINION_GAME)

    data object PhrasalVerbs : NavigationItem(Constants.PHRASAL_VERBS_GAME)

    data object KeywordsCheck : NavigationItem(Constants.KEYWORDS_CHECK_GAME)

    data object EssayBuilder : NavigationItem(Constants.ESSAY_BUILDER_GAME)

    data object Synonyms : NavigationItem(Constants.SYNONYMS_GAME)

    data object Home : NavigationItem("home")

    data object Profile : NavigationItem("profile")

    data object SeeMore : NavigationItem("see_more")
    
    data object Rating : NavigationItem("rating")

    data object GameCompletion : NavigationItem("game_completion")
}