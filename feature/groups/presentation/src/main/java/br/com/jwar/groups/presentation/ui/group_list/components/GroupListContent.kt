package br.com.jwar.groups.presentation.ui.group_list.components

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.jwar.groups.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.core.designsystem.components.EmptyContent
import br.com.jwar.sharedbill.core.designsystem.components.Title
import br.com.jwar.sharedbill.core.designsystem.theme.AppTheme
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.core.designsystem.theme.paddingMedium
import br.com.jwar.sharedbill.groups.presentation.R
import kotlinx.coroutines.launch

@Composable
fun GroupListContent(
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
    val topBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val floatingActionButtonExpanded = remember { groupListState.firstVisibleItemIndex == 0 }

    BackHandler(newGroupBottomSheetState.isVisible) {
        coroutineScope.launch { newGroupBottomSheetState.hide() }
    }

    ModalBottomSheetLayout(
        sheetState = newGroupBottomSheetState,
        sheetShape = MaterialTheme.shapes.medium,
        sheetBackgroundColor = MaterialTheme.colorScheme.background,
        sheetContent = {
            NewGroupBottomSheet(onGroupCreate, onGroupJoin)
        }
    ) {
        Scaffold(
            modifier = Modifier
                .padding(bottom = 80.dp)
                .nestedScroll(topBarScrollBehavior.nestedScrollConnection),
            topBar = {
                MediumTopAppBar(
                    title = { Title(stringResource(R.string.label_my_groups)) },
                    colors = TopAppBarDefaults.mediumTopAppBarColors(
                        scrolledContainerColor = Color.Unspecified,
                        containerColor = Color.Unspecified
                    ),
                    scrollBehavior = topBarScrollBehavior
                )
            },
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    onClick = { coroutineScope.launch { newGroupBottomSheetState.show() } },
                    expanded = floatingActionButtonExpanded,
                    icon = { Icon(Icons.Filled.Add, stringResource(R.string.label_group_new)) },
                    text = { Text(text = stringResource(R.string.label_group_new)) },
                )
            },
            floatingActionButtonPosition = FabPosition.End,
        ) { innerPadding ->
            Box (
                modifier = Modifier.padding(innerPadding)
            ) {
                if (groups.isEmpty()) {
                    EmptyContent(
                        image = painterResource(R.drawable.group_list_empty_img),
                        message = stringResource(R.string.message_groups_empty)
                    )
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
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun PreviewGroupList() {
    SharedBillTheme {
        Scaffold {
            GroupListContent(
                listOf(
                    GroupUiModel.sample(),
                    GroupUiModel.sample(),
                    GroupUiModel.sample(),
                )
            )
        }
    }
}