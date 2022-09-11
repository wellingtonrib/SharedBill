package br.com.jwar.sharedbill.presentation.navigation

const val AUTH_SCREEN = "auth"
const val HOME_SCREEN = "home"

sealed class AppScreen(val route: String) {
    object Auth: AppScreen(AUTH_SCREEN)
    object Home: AppScreen(HOME_SCREEN)
}