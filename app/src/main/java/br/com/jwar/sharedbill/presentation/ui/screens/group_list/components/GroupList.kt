package br.com.jwar.sharedbill.presentation.ui.screens.group_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.R
import br.com.jwar.sharedbill.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.presentation.ui.theme.AppTheme
import br.com.jwar.sharedbill.presentation.ui.theme.SharedBillTheme
import br.com.jwar.sharedbill.presentation.ui.theme.paddingMedium
import br.com.jwar.sharedbill.presentation.ui.theme.sizeLarge
import br.com.jwar.sharedbill.presentation.ui.theme.sizeMedium
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

@Composable
fun GroupsListItem(group: GroupUiModel, onGroupClick: (groupId: String) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = { onGroupClick(group.id) }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .sizeLarge()
                    .background(AppTheme.colors.primary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.sizeMedium(),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_baseline_groups_24),
                    tint = Color.White,
                    contentDescription = stringResource(R.string.description_group_image)
                )
            }
            Column(
                modifier = Modifier.paddingMedium()
            ) {
                Text(text = group.title)
                Text(text = stringResource(R.string.label_group_members_count, group.members.size))
            }
        }
    }
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