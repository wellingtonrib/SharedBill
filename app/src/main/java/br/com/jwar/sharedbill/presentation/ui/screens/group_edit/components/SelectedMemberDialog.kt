package br.com.jwar.sharedbill.presentation.ui.screens.group_edit.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import br.com.jwar.sharedbill.R
import br.com.jwar.sharedbill.presentation.models.UserUiModel
import br.com.jwar.sharedbill.presentation.ui.screens.account.components.UserInfo
import br.com.jwar.sharedbill.presentation.ui.theme.SharedBillTheme
import br.com.jwar.sharedbill.presentation.ui.theme.fillMaxWidthPaddingMedium

@Composable
fun SelectedMemberDialog(
    selectedMember: UserUiModel? = null,
    onMemberSelectionChange: (UserUiModel?) -> Unit = {}
) {
    selectedMember?.let { user ->
        Dialog(
            onDismissRequest = { onMemberSelectionChange(null) },
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
            ) {
                Column(
                    modifier = Modifier.fillMaxWidthPaddingMedium(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    UserInfo(user = user)
                    Text(text = user.getJoinInfo())
                    Button(onClick = { onMemberSelectionChange(null) }) {
                        Text(text = stringResource(R.string.label_ok))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSelectedMemberDialog() {
    SharedBillTheme {
        SelectedMemberDialog(
            selectedMember = UserUiModel.sample()
        )
    }
}