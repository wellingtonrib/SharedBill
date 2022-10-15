package br.com.jwar.sharedbill.presentation.ui.screens.group_details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import br.com.jwar.sharedbill.R
import br.com.jwar.sharedbill.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.presentation.ui.theme.horizontalSpaceSmall

@Composable
fun GroupInfo(
    group: GroupUiModel,
    onManageClick: () -> Unit
) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(R.string.label_group_details),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.Bold
            )
            Button(onClick = onManageClick) {
                Text(text = stringResource(R.string.label_group_manage))
            }
        }
        Row {
            Text(text = stringResource(R.string.label_group_title), fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.horizontalSpaceSmall())
            Text(text = group.title)
        }
        Row {
            Text(text = stringResource(R.string.label_group_members), fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.horizontalSpaceSmall())
            Text(text = group.members)
        }
    }
}