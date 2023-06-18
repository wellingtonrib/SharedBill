package br.com.jwar.sharedbill.account.presentation.screens.auth.components

import android.annotation.SuppressLint
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.account.presentation.R

@Composable
fun SignInButton(
    onSignInClick: () -> Unit = {}
) {
    Button(
        onClick = onSignInClick
    ) {
        Text(
            text = stringResource(R.string.label_sign_in)
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun PreviewSignInButton() {
    SharedBillTheme {
        Scaffold {
            SignInButton()
        }
    }
}