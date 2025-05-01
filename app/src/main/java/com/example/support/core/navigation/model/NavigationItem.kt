package com.example.support.core.navigation.model


sealed class NavigationItem(val route: String) {
    data object Register : NavigationItem("register")
    
    data object Login : NavigationItem("login")
    
    data object FactOpinion : NavigationItem("fact_opinion")

    data object PhrasalVerbs : NavigationItem("phrasal_verbs")

    data object KeywordsCheck : NavigationItem("keywords_check")

    data object EssayBuilder : NavigationItem("essay_builder")

    data object Synonyms : NavigationItem("synonyms")

    data object Home : NavigationItem("home")

    data object Profile : NavigationItem("profile")

    data object SeeMore : NavigationItem("see_more")
    
    data object Rating : NavigationItem("rating")

    data object GameCompletion : NavigationItem("game_completion")
}