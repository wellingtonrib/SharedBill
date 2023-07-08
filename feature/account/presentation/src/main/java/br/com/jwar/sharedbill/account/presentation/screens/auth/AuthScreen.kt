package br.com.jwar.sharedbill.account.presentation.screens.auth

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.jwar.sharedbill.account.presentation.R
import br.com.jwar.sharedbill.account.presentation.screens.auth.AuthContract.State
import br.com.jwar.sharedbill.account.presentation.screens.auth.AuthContract.State.*
import br.com.jwar.sharedbill.account.presentation.screens.auth.components.SignInButton
import br.com.jwar.sharedbill.core.designsystem.components.LoadingContent
import br.com.jwar.sharedbill.core.designsystem.theme.AppTheme
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.core.designsystem.theme.VerticalSpacerLarge
import br.com.jwar.sharedbill.core.designsystem.R as DS

@Composable
fun AuthScreen(
    state: State,
    onSignInClick: () -> Unit = {}
) {
    when(state) {
        is Loading -> LoadingContent()
        is Idle -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier.size(100.dp),
                        painter = painterResource(DS.drawable.app_icon),
                        contentDescription = stringResource(R.string.description_app_logo)
                    )
                    Text(
                        text = stringResource(DS.string.app_name),
                        fontSize = 32.sp,
                        fontWeight = FontWeight(600),
                        color = AppTheme.colors.primary,
                        style = AppTheme.typo.titleLarge
                    )
                    VerticalSpacerLarge()
                    SignInButton(onSignInClick)
                }
            }
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun PreviewAuthScreen() {
    SharedBillTheme {
        Scaffold {
            AuthScreen(
                state = Idle
            )
        }
    }
}
