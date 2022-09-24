package br.com.jwar.sharedbill.presentation.navigation

import br.com.jwar.sharedbill.R

enum class BottomNavItem(val title: String, val icon: Int, val route: String) {
    Groups("Groups", R.drawable.ic_baseline_groups_24, GROUPS_SCREEN),
    Account("Account", R.drawable.ic_baseline_account_circle_24, ACCOUNT_SCREEN)
}