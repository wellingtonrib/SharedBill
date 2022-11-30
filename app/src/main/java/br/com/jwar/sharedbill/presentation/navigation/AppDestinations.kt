package br.com.jwar.sharedbill.presentation.navigation

import br.com.jwar.sharedbill.presentation.navigation.AppDestinationsArgs.GROUP_ID_ARG
import br.com.jwar.sharedbill.presentation.navigation.AppScreens.ACCOUNT_SCREEN
import br.com.jwar.sharedbill.presentation.navigation.AppScreens.AUTH_SCREEN
import br.com.jwar.sharedbill.presentation.navigation.AppScreens.GROUP_DETAILS_SCREEN
import br.com.jwar.sharedbill.presentation.navigation.AppScreens.GROUP_EDIT_SCREEN
import br.com.jwar.sharedbill.presentation.navigation.AppScreens.GROUP_LIST_SCREEN
import br.com.jwar.sharedbill.presentation.navigation.AppScreens.PAYMENT_SCREEN

object AppDestinationsArgs {
    const val GROUP_ID_ARG = "groupId"
}

object AppDestinations {
    const val AUTH_ROUTE = AUTH_SCREEN
    const val ACCOUNT_ROUTE = ACCOUNT_SCREEN
    const val GROUP_LIST_ROUTE = GROUP_LIST_SCREEN
    const val GROUP_DETAILS_ROUTE = "$GROUP_DETAILS_SCREEN/{$GROUP_ID_ARG}"
    const val GROUP_EDIT_ROUTE = "$GROUP_EDIT_SCREEN/{$GROUP_ID_ARG}"
    const val PAYMENT_ROUTE = "$PAYMENT_SCREEN/{$GROUP_ID_ARG}"
}
