package br.com.jwar.groups.presentation.ui.group_details.components

import androidx.compose.material3.Icon
import androidx.compose.material3.LeadingIconTab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun GroupDetailsTabBar(
    pages: List<Page>,
    currentPage: Int,
    onPageSelected: (Int) -> Unit
) {
    TabRow(
        containerColor = Color.Unspecified,
        selectedTabIndex = currentPage,
    ) {
        pages.forEachIndexed { index, (icon, text) ->
            LeadingIconTab(
                selected = currentPage == index,
                onClick = { onPageSelected(index) },
                icon = { Icon(imageVector = icon, contentDescription = text) },
                text = { Text(text = text) }
            )
        }
    }
}