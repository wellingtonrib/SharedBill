package br.com.jwar.groups.presentation.ui.group_edit.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.groups.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.core.designsystem.components.TextFieldWithSuggestions
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.groups.presentation.R

@Composable
fun GroupEditHeader(
    group: GroupUiModel,
    onGroupUpdated: (GroupUiModel) -> Unit = {},
) {
    val suggestions = stringArrayResource(R.array.samples_group_title)

    TextFieldWithSuggestions(
        modifier = Modifier
            .fillMaxWidth(),
        value = group.title,
        suggestions = suggestions.toList(),
        label = { Text(stringResource(R.string.label_group_title)) },
        placeholder = { Text(stringResource(R.string.placeholder_group_title)) },
        onValueChange = { newValue ->
            onGroupUpdated(group.copy(title = newValue.text))
        },
    )
}

@Preview
@Composable
fun PreviewGroupEditHeader() {
    SharedBillTheme {
        GroupEditHeader(group = GroupUiModel.sample())
    }
}