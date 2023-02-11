package br.com.jwar.sharedbill.presentation.screens.account.components

import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.R
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme

@Composable
fun SignOutButton(
    onSignOutClick: () -> Unit = {}
) {
    Button(onClick = onSignOutClick) {
        Text(text = stringResource(R.string.label_logout))
    }
}

@Preview
@Composable
fun PreviewSignInButton() {
    SharedBillTheme {
        Scaffold {
            SignOutButton()
        }
    }
}