package br.com.jwar.sharedbill.presentation.navigation

const val AUTH_SCREEN = "auth"
const val ACCOUNT_SCREEN = "account"
const val GROUP_LIST_SCREEN = "group_list"
const val GROUP_DETAILS_SCREEN = "group_details/{$GROUP_ID_ARG}"
const val GROUP_EDIT_SCREEN = "group_edit/{$GROUP_ID_ARG}"
const val PAYMENT_SCREEN = "group_payment/{$GROUP_ID_ARG}"

sealed class AppScreen(val route: String) {
    object Auth: AppScreen(AUTH_SCREEN)
    object Account: AppScreen(ACCOUNT_SCREEN)
    object GroupList: AppScreen(GROUP_LIST_SCREEN)
    object GroupDetails: AppScreen(GROUP_DETAILS_SCREEN) {
        fun createRoute(groupId: String) = GROUP_DETAILS_SCREEN.replace("{$GROUP_ID_ARG}",groupId)
    }
    object GroupEdit: AppScreen(GROUP_EDIT_SCREEN) {
        fun createRoute(groupId: String) = GROUP_EDIT_SCREEN.replace("{$GROUP_ID_ARG}",groupId)
    }
    object Payment: AppScreen(PAYMENT_SCREEN) {
        fun createRoute(groupId: String) = PAYMENT_SCREEN.replace("{$GROUP_ID_ARG}",groupId)
    }
}