package br.com.jwar.sharedbill.presentation.ui.screens.group_edit.components


import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.R
import br.com.jwar.sharedbill.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.presentation.models.UserUiModel
import br.com.jwar.sharedbill.presentation.navigation.AppTopBar
import br.com.jwar.sharedbill.presentation.ui.theme.VerticalSpacerMedium
import br.com.jwar.sharedbill.presentation.ui.theme.fillMaxWidthPaddingMedium

@Composable
fun GroupEditForm(
    group: GroupUiModel,
    selectedMember: UserUiModel? = null,
    onGroupUpdated: (GroupUiModel) -> Unit = {},
    onSaveMemberClick: (String) -> Unit = {},
    onMemberSelectionChange: (UserUiModel?) -> Unit = {},
    onMemberDeleteClick: (String) -> Unit = {},
    onSaveClick: () -> Unit = {},
    onNavigateBack: () -> Unit = {},
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val listState = rememberLazyListState()

    SelectedMemberDialog(selectedMember, onMemberSelectionChange)

    Scaffold(
        topBar = {
            AppTopBar(
                navigationBack = { onNavigateBack() },
                title = stringResource(id = R.string.label_group_edit),
                actions = {
                    IconButton(onClick = { onSaveClick(); keyboardController?.hide() }) {
                        Icon(Icons.Filled.Done, stringResource(id = R.string.label_group_manage))
                    }
                }
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
        GroupEditForm(group = GroupUiModel.sample(),)
    }
}