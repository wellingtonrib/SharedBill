package br.com.jwar.sharedbill.groups.presentation.ui.group_list.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.jwar.sharedbill.core.designsystem.components.InputDialog
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.core.designsystem.theme.paddingMedium
import br.com.jwar.sharedbill.groups.presentation.R
import br.com.jwar.sharedbill.core.designsystem.R as DSR

@Composable
fun NewGroupBottomSheet(
    onGroupCreate: (title: String) -> Unit = {},
    onGroupJoin: (inviteCode: String) -> Unit = {}
) {
    val openGroupJoinDialog = remember { mutableStateOf(false) }
    if (openGroupJoinDialog.value) {
        InputDialog(
            label = stringResource(R.string.label_group_invite_code),
            action = stringResource(DSR.string.label_verify),
            onDismiss = { openGroupJoinDialog.value = false },
            onAction = { openGroupJoinDialog.value = false; onGroupJoin(it) }
        )
    }

    Column(
        modifier = Modifier
            .heightIn(min = 200.dp, max = 200.dp)
            .paddingMedium()
            .padding(bottom = 120.dp)
            .fillMaxWidth(),
        horizontalAlignment = CenterHorizontally
    ) {
        Button(onClick = { onGroupCreate("") }) {
            Text(text = stringResource(id = R.string.label_group_create))
        }
        Button(onClick = { openGroupJoinDialog.value = true }) {
            Text(text = stringResource(id = R.string.label_group_join))
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun PreviewNewGroupBottomSheet() {
    SharedBillTheme {
        Scaffold {
            NewGroupBottomSheet()
        }
    }
}