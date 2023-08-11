package br.com.jwar.sharedbill.account.presentation.ui.account.components

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.jwar.sharedbill.account.presentation.R
import br.com.jwar.sharedbill.account.presentation.ui.account.AccountContract
import br.com.jwar.sharedbill.core.designsystem.R.drawable.app_icon
import br.com.jwar.sharedbill.core.designsystem.components.InfoDialog
import br.com.jwar.sharedbill.core.designsystem.components.UserCard
import br.com.jwar.sharedbill.core.designsystem.model.UserUiModel
import br.com.jwar.sharedbill.core.designsystem.theme.AppTheme
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.core.designsystem.theme.VerticalSpacerLarge

@Composable
fun AccountContent(
    state: AccountContract.State,
    onSignOutClick: () -> Unit = {},
    onSupportClick: () -> Unit = {},
    onAboutClick: () -> Unit = {},
    onAboutDismiss: () -> Unit = {},
    onTermsClick: () -> Unit = {},
    onPrivacyClick: () -> Unit = {},
    onRateUsClick: () -> Unit = {},
) {
    if (state.showAboutDialog) {
        InfoDialog(
            image = app_icon,
            title = stringResource(R.string.label_about),
            message = stringResource(R.string.message_about),
            onDismiss = onAboutDismiss,
            onAction = onAboutDismiss
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        VerticalSpacerLarge()
        UserCard(
            user = state.uiModel,
            avatarSize = 80.dp,
        )
        VerticalSpacerLarge()
        Column(
            modifier = Modifier.padding(horizontal = AppTheme.dimens.space_8),
            verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.space_4)
        ) {
            AccountAction(
                imageVector = Icons.Outlined.Email,
                title = stringResource(R.string.label_support),
                onClick = onSupportClick,
            )
            AccountAction(
                imageVector = Icons.Outlined.Info,
                title = stringResource(R.string.label_about),
                onClick = onAboutClick,
            )
            AccountAction(
                imageVector = Icons.Outlined.Star,
                title = stringResource(R.string.label_rate),
                onClick = onRateUsClick,
            )
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
            AccountAction(
                imageVector = Icons.Outlined.ExitToApp,
                title = stringResource(R.string.label_logout),
                onClick = onSignOutClick,
            )
        }
    }
}

@Composable
@Preview
fun PreviewAccountForm() {
    SharedBillTheme {
        AccountContent(
            state = AccountContract.State(
                uiModel = UserUiModel.sample()
            )
        )
    }
}
