package br.com.jwar.sharedbill.presentation.ui.screens.group_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.presentation.ui.theme.AppTheme
import br.com.jwar.sharedbill.presentation.ui.theme.SharedBillTheme
import br.com.jwar.sharedbill.presentation.ui.theme.verticalPaddingMedium

@Composable
fun GroupList(
    groups: List<GroupUiModel>,
    onGroupClick: (groupId: String) -> Unit = {},
) {
    LazyColumn(
        modifier = Modifier.verticalPaddingMedium(),
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.space_4),
        content = {
            items(groups) { group ->
                GroupsListItem(group, onGroupClick)
            }
        }
    )
}

@Preview
@Composable
fun PreviewGroupList() {
    SharedBillTheme {
        Scaffold {
            GroupList(
                listOf(
                    GroupUiModel.sample(),
                    GroupUiModel.sample(),
                    GroupUiModel.sample(),
                )
            )
        }
    }
}