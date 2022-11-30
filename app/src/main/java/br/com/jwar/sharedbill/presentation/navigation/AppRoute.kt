package br.com.jwar.sharedbill.presentation.navigation

import br.com.jwar.sharedbill.presentation.navigation.AppDestinations.ACCOUNT_ROUTE
import br.com.jwar.sharedbill.presentation.navigation.AppDestinations.AUTH_ROUTE
import br.com.jwar.sharedbill.presentation.navigation.AppDestinations.GROUP_DETAILS_ROUTE
import br.com.jwar.sharedbill.presentation.navigation.AppDestinations.GROUP_EDIT_ROUTE
import br.com.jwar.sharedbill.presentation.navigation.AppDestinations.GROUP_LIST_ROUTE
import br.com.jwar.sharedbill.presentation.navigation.AppDestinations.PAYMENT_ROUTE
import br.com.jwar.sharedbill.presentation.navigation.AppDestinationsArgs.GROUP_ID_ARG

sealed class AppRoute(val route: String) {
    object Auth: AppRoute(AUTH_ROUTE)
    object Account: AppRoute(ACCOUNT_ROUTE)
    object GroupList: AppRoute(GROUP_LIST_ROUTE)
    object GroupDetails: AppRoute(GROUP_DETAILS_ROUTE) {
        fun createRoute(groupId: String) = GROUP_DETAILS_ROUTE.replace("{$GROUP_ID_ARG}", groupId)
    }
    object GroupEdit: AppRoute(GROUP_EDIT_ROUTE) {
        fun createRoute(groupId: String) = GROUP_EDIT_ROUTE.replace("{$GROUP_ID_ARG}", groupId)
    }
    object Payment: AppRoute(PAYMENT_ROUTE) {
        fun createRoute(groupId: String) = PAYMENT_ROUTE.replace("{$GROUP_ID_ARG}", groupId)
    }
}