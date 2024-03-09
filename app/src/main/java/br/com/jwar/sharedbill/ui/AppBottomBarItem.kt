package br.com.jwar.sharedbill.ui

import br.com.jwar.sharedbill.account.presentation.navigation.ACCOUNT_ROUTE
import br.com.jwar.sharedbill.core.designsystem.theme.Icons
import br.com.jwar.sharedbill.groups.presentation.ui.list.GROUP_LIST_ROUTE
import br.com.jwar.sharedbill.account.presentation.R as AR
import br.com.jwar.sharedbill.groups.presentation.R as GR

enum class AppBottomBarItem(val title: Int, val icon: Int, val route: String) {
    Groups(GR.string.label_groups, Icons.Groups, GROUP_LIST_ROUTE),
    Account(AR.string.label_account, Icons.Account, ACCOUNT_ROUTE)
}
