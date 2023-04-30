package br.com.jwar.sharedbill.ui

import br.com.jwar.groups.presentation.screens.group_list.GROUP_LIST_ROUTE
import br.com.jwar.sharedbill.R
import br.com.jwar.sharedbill.account.presentation.navigation.ACCOUNT_ROUTE
import br.com.jwar.sharedbill.core.designsystem.theme.Icons

enum class AppBottomBarItem(val title: Int, val icon: Int, val route: String) {
    Groups(R.string.label_groups, Icons.Groups, GROUP_LIST_ROUTE),
    Account(R.string.label_account, Icons.Account, ACCOUNT_ROUTE)
}