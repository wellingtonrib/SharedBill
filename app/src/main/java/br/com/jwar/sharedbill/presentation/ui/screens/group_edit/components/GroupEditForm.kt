package br.com.jwar.sharedbill.presentation.ui.screens.group_edit.components


import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.presentation.models.UserUiModel
import br.com.jwar.sharedbill.presentation.ui.theme.*

@Composable
fun GroupEditForm(
    group: GroupUiModel,
    selectedMember: UserUiModel? = null,
    onGroupUpdated: (GroupUiModel) -> Unit = {},
    onSaveMemberClick: (String) -> Unit = {},
    onMemberSelectionChange: (UserUiModel?) -> Unit = {},
    onMemberDeleteClick: (String) -> Unit = {},
) {
    val listState = rememberLazyListState()
    SelectedMemberDialog(selectedMember, onMemberSelectionChange)
    Scaffold(
        floatingActionButton = { GroupEditFooter(listState, onSaveMemberClick) },
        floatingActionButtonPosition = FabPosition.End,
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxWidthPaddingMedium(),
        ) {
            item {
                GroupEditHeader(group, onGroupUpdated)
                Spacer(modifier = Modifier.verticalSpaceMedium())
            }
            item {
                GroupEditMembersHeader()
            }
            items(group.members) { member ->
                Spacer(modifier = Modifier.verticalSpaceMedium())
                GroupMemberCard(
                    member = member,
                    onMemberSelect = { onMemberSelectionChange(it) },
                    onMemberDelete = { onMemberDeleteClick(it) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGroupEditForm() {
    MaterialTheme {
        GroupEditForm(group = GroupUiModel.sample())
    }
}