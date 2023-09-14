package br.com.jwar.sharedbill.account.presentation.ui.account.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.jwar.sharedbill.account.presentation.R
import br.com.jwar.sharedbill.account.presentation.ui.account.AccountContract
import br.com.jwar.sharedbill.core.designsystem.components.UserCard
import br.com.jwar.sharedbill.core.designsystem.model.UserUiModel
import br.com.jwar.sharedbill.core.designsystem.theme.AppTheme
import br.com.jwar.sharedbill.core.designsystem.theme.HorizontalSpacerSmall
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.core.designsystem.theme.VerticalSpacerLarge

@Composable
fun AccountContent(
    state: AccountContract.State,
    onSignOutClick: () -> Unit = {},
    onContactClick: () -> Unit = {},
    onAboutClick: () -> Unit = {},
    onTermsClick: () -> Unit = {},
    onPrivacyClick: () -> Unit = {},
    onRateUsClick: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = AppTheme.dimens.space_10)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.space_10)
    ) {
        UserCard(
            user = state.uiModel,
            avatarSize = 80.dp,
        )
        Column(
            modifier = Modifier.padding(horizontal = AppTheme.dimens.space_8),
            verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.space_4),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AccountAction(
                imageVector = Icons.Outlined.Info,
                title = stringResource(R.string.label_about),
                onClick = onAboutClick,
            )
            AccountAction(
                imageVector = Icons.Outlined.Email,
                title = stringResource(R.string.label_contact),
                onClick = onContactClick,
            )
//            AccountAction(
//                imageVector = Icons.Outlined.Star,
//                title = stringResource(R.string.label_rate),
//                onClick = onRateUsClick,
//            )
            AccountAction(
                imageVector = Icons.Outlined.Menu,
                title = stringResource(R.string.label_terms),
                onClick = onTermsClick,
            )
            AccountAction(
                imageVector = Icons.Outlined.Lock,
                title = stringResource(R.string.label_privacy),
                onClick = onPrivacyClick,
            )
        }
        Box(
            modifier = Modifier.weight(1f)
        ) {
            Button(
                modifier = Modifier.align(Alignment.BottomCenter),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = MaterialTheme.colorScheme.primary
                ),
                onClick = onSignOutClick
            ) {
                Icon(imageVector = Icons.Outlined.ExitToApp, contentDescription = null)
                HorizontalSpacerSmall()
                Text(stringResource(R.string.label_logout))
            }
        }
        VerticalSpacerLarge()
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewAccountForm() {
    SharedBillTheme {
        AccountContent(
            state = AccountContract.State(
                uiModel = UserUiModel.sample()
            )
        )
    }
}
