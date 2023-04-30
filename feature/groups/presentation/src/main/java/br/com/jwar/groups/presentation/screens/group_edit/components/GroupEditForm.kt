package br.com.jwar.groups.presentation.screens.group_edit.components


import FormTopBar
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.groups.presentation.models.GroupMemberUiModel
import br.com.jwar.groups.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.core.designsystem.theme.VerticalSpacerMedium
import br.com.jwar.sharedbill.core.designsystem.theme.fillMaxWidthPaddingMedium
import br.com.jwar.sharedbill.groups.presentation.R

@Composable
fun GroupEditForm(
    group: GroupUiModel,
    selectedMember: GroupMemberUiModel? = null,
    onGroupUpdated: (GroupUiModel) -> Unit = {},
    onSaveMemberClick: (String) -> Unit = {},
    onMemberSelectionChange: (GroupMemberUiModel?) -> Unit = {},
    onMemberDeleteClick: (String) -> Unit = {},
    onSaveClick: () -> Unit = {},
    onNavigateBack: () -> Unit = {},
) {
    val listState = rememberLazyListState()

    SelectedMemberDialog(selectedMember, onMemberSelectionChange)

    Scaffold(
        topBar = {
            FormTopBar(
                onNavigateBack = { onNavigateBack() },
                title = stringResource(id = R.string.label_group_edit),
                onSaveClick = { onSaveClick() }
            )
        },
        floatingActionButton = {
            GroupEditFooter(listState, onSaveMemberClick)
        },
        floatingActionButtonPosition = FabPosition.End,
    ) { innerPadding ->
        LazyColumn(
            state = listState,
            contentPadding = innerPadding,
            modifier = Modifier.fillMaxWidthPaddingMedium(),
        ) {
            item {
                GroupEditHeader(group, onGroupUpdated)
                VerticalSpacerMedium()
            }
            item {
                GroupEditMembersHeader()
            }
            items(group.members) { member ->
                VerticalSpacerMedium()
                GroupMemberCard(
                    member = member,
                    onMemberSelect = { onMemberSelectionChange(it) }
                ) { onMemberDeleteClick(it) }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGroupEditForm() {
    MaterialTheme {
        GroupEditForm(group = GroupUiModel.sample(),)
    }
}