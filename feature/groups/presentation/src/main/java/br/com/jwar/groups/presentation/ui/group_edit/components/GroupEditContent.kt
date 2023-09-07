package br.com.jwar.groups.presentation.ui.group_edit.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.groups.presentation.models.GroupMemberUiModel
import br.com.jwar.groups.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.core.designsystem.components.AppTopBar
import br.com.jwar.sharedbill.core.designsystem.components.CloseNavigationIcon
import br.com.jwar.sharedbill.core.designsystem.components.TextFieldWithSuggestions
import br.com.jwar.sharedbill.core.designsystem.theme.AppTheme
import br.com.jwar.sharedbill.core.designsystem.theme.paddingMedium
import br.com.jwar.sharedbill.groups.presentation.R
import br.com.jwar.sharedbill.core.designsystem.R as DSR

@Composable
fun GroupEditContent(
    group: GroupUiModel,
    selectedMember: GroupMemberUiModel? = null,
    onGroupUpdated: (GroupUiModel) -> Unit = {},
    onSaveMemberClick: (String) -> Unit = {},
    onMemberSelectionChange: (GroupMemberUiModel?) -> Unit = {},
    onMemberDeleteClick: (String) -> Unit = {},
    onShareInviteCodeClick: (String) -> Unit = {},
    onSaveClick: () -> Unit = {},
    onNavigateBack: () -> Unit = {},
) {
    val listState = rememberLazyListState()
    val suggestions = stringArrayResource(R.array.samples_group_title)
    val keyboardController = LocalSoftwareKeyboardController.current

    SelectedMemberDialog(selectedMember, onMemberSelectionChange, onShareInviteCodeClick)

    Scaffold(
        topBar = {
            AppTopBar(
                navigationBack = onNavigateBack,
                navigationIcon = { CloseNavigationIcon(onNavigateBack) },
                title = stringResource(id = R.string.label_group_edit),
                actions = {
                    IconButton(onClick = { onSaveClick(); keyboardController?.hide() }) {
                        Icon(Icons.Filled.Done, stringResource(id = DSR.string.description_done))
                    }
                }
            )
        },
        floatingActionButton = {
            GroupEditFooter(listState, onSaveMemberClick)
        },
        floatingActionButtonPosition = FabPosition.End,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            TextFieldWithSuggestions(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = AppTheme.dimens.space_8, end = AppTheme.dimens.space_8),
                label = { Text(stringResource(R.string.label_group_title)) },
                placeholder = { Text(stringResource(R.string.placeholder_group_title)) },
                text = group.title,
                suggestions = suggestions.toList(),
                onValueChange = { newValue ->
                    onGroupUpdated(group.copy(title = newValue.text))
                },
            )
            Text(
                modifier = Modifier.paddingMedium(),
                text = stringResource(R.string.label_group_members),
                style = AppTheme.typo.titleLarge,
            )
            LazyColumn(
                modifier = Modifier
                    .padding(start = AppTheme.dimens.space_8, end = AppTheme.dimens.space_8),
                state = listState,
                verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.space_4),
            ) {
                items(group.members.asList()) { member ->
                    GroupMemberCard(
                        member = member,
                        onMemberSelect = { onMemberSelectionChange(it) }
                    ) { onMemberDeleteClick(it) }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGroupEditForm() {
    MaterialTheme {
        GroupEditContent(group = GroupUiModel.sample(),)
    }
}