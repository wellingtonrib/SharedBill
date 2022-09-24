package br.com.jwar.sharedbill.presentation.navigation

const val AUTH_SCREEN = "auth"
const val ACCOUNT_SCREEN = "account"
const val GROUPS_SCREEN = "groups"

sealed class AppScreen(val route: String) {
    object Auth: AppScreen(AUTH_SCREEN)
    object Account: AppScreen(ACCOUNT_SCREEN)
    object Groups: AppScreen(GROUPS_SCREEN)
}