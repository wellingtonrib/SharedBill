package br.com.jwar.groups.presentation.screens.group_edit.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.groups.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.core.designsystem.theme.fillMaxWidthPaddingMedium
import br.com.jwar.sharedbill.groups.presentation.R

@Composable
fun GroupEditHeader(
    group: GroupUiModel,
    onGroupUpdated: (GroupUiModel) -> Unit = {},
) {
    val titleFocusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        if (group.title.isBlank()) {
            titleFocusRequester.requestFocus()
        }
    }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(titleFocusRequester),
        shape = MaterialTheme.shapes.medium,
        value = group.title,
        label = { Text(text = stringResource(R.string.label_group_title)) },
        placeholder = { Text(text = stringResource(R.string.placeholder_group_title)) },
        onValueChange = { onGroupUpdated(group.copy(title = it)) },
    )
}

@Preview
@Composable
fun PreviewGroupEditHeader() {
    SharedBillTheme {
        GroupEditHeader(group = GroupUiModel.sample())
    }
}