package br.com.jwar.sharedbill.presentation.screens.group_edit.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.R
import br.com.jwar.sharedbill.core.designsystem.theme.*
import br.com.jwar.sharedbill.presentation.models.GroupUiModel

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

    Card {
        Row(
            modifier = Modifier.padding(end = AppTheme.dimens.space_4),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .sizeLarge()
                    .background(AppTheme.colors.primary),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier.sizeMedium(),
                    imageVector = ImageVector.vectorResource(Icons.Groups),
                    contentDescription = stringResource(R.string.description_group_image),
                    colorFilter = ColorFilter.tint(color = AppTheme.colors.onPrimary),
                    contentScale = ContentScale.Crop
                )
            }
            HorizontalSpacerMedium()
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
            HorizontalSpacerLarge()
        }
    }
}

@Preview
@Composable
fun PreviewGroupEditHeader() {
    SharedBillTheme {
        GroupEditHeader(group = GroupUiModel.sample())
    }
}