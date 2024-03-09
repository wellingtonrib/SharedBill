package br.com.jwar.sharedbill.groups.presentation.ui.list.components

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
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.jwar.sharedbill.core.designsystem.components.EmptyContent
import br.com.jwar.sharedbill.core.designsystem.components.InputDialog
import br.com.jwar.sharedbill.core.designsystem.components.Title
import br.com.jwar.sharedbill.core.designsystem.theme.AppTheme
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.core.designsystem.theme.paddingMedium
import br.com.jwar.sharedbill.groups.presentation.R
import br.com.jwar.sharedbill.groups.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.testing.TestTags
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import br.com.jwar.sharedbill.core.designsystem.R as DSR

@Composable
fun GroupListContent(
    groups: List<GroupUiModel>,
    snackbarHostState: SnackbarHostState = SnackbarHostState(),
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
    val floatingActionButtonExpanded by remember {
        derivedStateOf {
            groupListState.firstVisibleItemIndex == 0
        }
    }
    fun CoroutineScope.hideNewGroupBottomSheet() = launch { newGroupBottomSheetState.hide() }

    BackHandler(newGroupBottomSheetState.isVisible) {
        coroutineScope.hideNewGroupBottomSheet()
    }

    val openGroupJoinDialog = remember { mutableStateOf(false) }
    if (openGroupJoinDialog.value) {
        InputDialog(
            label = stringResource(R.string.label_group_invite_code),
            action = stringResource(DSR.string.label_verify),
            onDismiss = { openGroupJoinDialog.value = false },
            onAction = {
                openGroupJoinDialog.value = false
                onGroupJoin(it)
            }
        )
    }

    ModalBottomSheetLayout(
        sheetState = newGroupBottomSheetState,
        sheetShape = MaterialTheme.shapes.medium,
        sheetBackgroundColor = MaterialTheme.colorScheme.background,
        sheetContent = {
            NewGroupBottomSheet(
                modifier = Modifier.padding(top = 16.dp, bottom = 120.dp),
                onGroupCreate = {
                    coroutineScope.hideNewGroupBottomSheet()
                    onGroupCreate("")
                },
                onGroupJoin = {
                    coroutineScope.hideNewGroupBottomSheet()
                    openGroupJoinDialog.value = true
                }
            )
        }
    ) {
        Scaffold(
            modifier = Modifier
                .testTag(TestTags.GroupListContent)
                .padding(bottom = 70.dp)
                .nestedScroll(topBarScrollBehavior.nestedScrollConnection),
            snackbarHost = { SnackbarHost(snackbarHostState) },
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
            Box(
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
                            GroupCard(group, onGroupClick, onGroupDelete, onGroupLeave)
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
                ),
            )
        }
    }
}
