package br.com.jwar.sharedbill.presentation.navigation

const val AUTH_SCREEN = "auth"
const val ACCOUNT_SCREEN = "account"
const val GROUP_LIST_SCREEN = "group_list"
const val GROUP_DETAILS_SCREEN = "group_details/{groupId}"
const val GROUP_CREATE_SCREEN = "group_create"

sealed class AppScreen(val route: String) {
    object Auth: AppScreen(AUTH_SCREEN)
    object Account: AppScreen(ACCOUNT_SCREEN)
    object GroupList: AppScreen(GROUP_LIST_SCREEN)
    object GroupDetails: AppScreen(GROUP_DETAILS_SCREEN) {
        fun createRoute(groupId: String) = "group_details/$groupId"
    }
    object GroupCreate: AppScreen(GROUP_CREATE_SCREEN)
}