package br.com.jwar.groups.presentation.screens.group_list.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.jwar.groups.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.core.designsystem.components.EmptyContent
import br.com.jwar.sharedbill.core.designsystem.theme.AppTheme
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.core.designsystem.theme.paddingMedium
import br.com.jwar.sharedbill.groups.presentation.R
import kotlinx.coroutines.launch

@Composable
fun GroupList(
    groups: List<GroupUiModel>,
    onGroupClick: (groupId: String) -> Unit = {},
    onGroupCreate: (title: String) -> Unit = {},
    onGroupJoin: (inviteCode: String) -> Unit = {},
    onGroupDelete: (groupId: String) -> Unit = {},
    onGroupLeave: (groupId: String) -> Unit = {},
) {
    val coroutineScope = rememberCoroutineScope()
    val newGroupBottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val groupListState = rememberLazyListState()

    BackHandler(newGroupBottomSheetState.isVisible) {
        coroutineScope.launch { newGroupBottomSheetState.hide() }
    }

    ModalBottomSheetLayout(
        sheetState = newGroupBottomSheetState,
        sheetShape = MaterialTheme.shapes.medium,
        sheetContent = {
            NewGroupBottomSheet(onGroupCreate, onGroupJoin)
        }
    ) {
        Scaffold(
            modifier = Modifier.padding(bottom = 80.dp),
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    onClick = { coroutineScope.launch { newGroupBottomSheetState.show() } },
                    expanded = groupListState.firstVisibleItemIndex == 0,
                    icon = { Icon(Icons.Filled.Add, stringResource(R.string.label_group_new)) },
                    text = { Text(text = stringResource(R.string.label_group_new)) },
                )
            },
            floatingActionButtonPosition = FabPosition.End,
        ) {
            if (groups.isEmpty()) {
                EmptyContent(message = stringResource(id = R.string.message_groups_empty))
                return@Scaffold
            }
            LazyColumn(
                state = groupListState,
                modifier = Modifier.paddingMedium(),
                verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.space_4),
                content = {
                    items(groups) { group ->
                        GroupListItem(group, onGroupClick, onGroupDelete, onGroupLeave)
                    }
                }
            )
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
                ),
            )
        }
    }
}