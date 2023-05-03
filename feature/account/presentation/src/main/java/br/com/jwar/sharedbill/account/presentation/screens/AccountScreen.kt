package br.com.jwar.sharedbill.account.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.account.presentation.model.UserUiModel
import br.com.jwar.sharedbill.core.designsystem.components.LoadingContent
import br.com.jwar.sharedbill.core.designsystem.components.UserCard
import br.com.jwar.sharedbill.account.presentation.screens.AccountContract.State
import br.com.jwar.sharedbill.account.presentation.screens.AccountContract.State.*
import br.com.jwar.sharedbill.account.presentation.screens.components.SignInButton
import br.com.jwar.sharedbill.account.presentation.screens.components.SignOutButton
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.core.designsystem.theme.VerticalSpacerMedium

@Composable
fun AccountScreen(
    state: State,
    onSignOutClick: () -> Unit = {},
    onSignInClick: () -> Unit = {}
) {
    when(state) {
        is Loading -> LoadingContent()
        is Loaded -> {
            if (state.isLoggedIn) {
                UserInfo(state, onSignOutClick)
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    SignInButton(onSignInClick)
                }
            }
        }
        is Error -> {
            Text(text = state.message)
            SignOutButton(onSignOutClick)
        }
    }
}

@Composable
private fun UserInfo(
    state: Loaded,
    onSignOutClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        UserCard(state.uiModel.toUserCardUiModel())
        VerticalSpacerMedium()
        SignOutButton(onSignOutClick)
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun PreviewAccountContent() {
    SharedBillTheme {
        Scaffold {
            AccountScreen(
                state = Loaded(
                    UserUiModel.sample()
                )
            )
        }
    }
}
