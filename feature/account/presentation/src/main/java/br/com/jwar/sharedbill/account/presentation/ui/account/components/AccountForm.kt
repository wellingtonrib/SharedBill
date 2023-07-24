package br.com.jwar.sharedbill.account.presentation.ui.account.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.jwar.sharedbill.account.presentation.R
import br.com.jwar.sharedbill.account.presentation.ui.account.AccountContract
import br.com.jwar.sharedbill.core.designsystem.components.InfoDialog
import br.com.jwar.sharedbill.core.designsystem.components.UserCard
import br.com.jwar.sharedbill.core.designsystem.theme.AppTheme
import br.com.jwar.sharedbill.core.designsystem.theme.VerticalSpacerLarge
import br.com.jwar.sharedbill.core.designsystem.theme.VerticalSpacerMedium
import br.com.jwar.sharedbill.core.designsystem.R.drawable.app_icon

@Composable
fun AccountForm(
    state: AccountContract.State,
    onSignOutClick: () -> Unit,
    onSupportClick: () -> Unit,
    onAboutClick: () -> Unit,
    onAboutDismiss: () -> Unit,
    onTermsClick: () -> Unit,
    onPrivacyClick: () -> Unit,
    onRateUsClick: () -> Unit,
) {
    val listState = rememberLazyListState()

    if (state.showAboutDialog) {
        InfoDialog(
            image = app_icon,
            title = stringResource(R.string.label_about),
            message = stringResource(R.string.message_about),
            onDismiss = onAboutDismiss,
            onAction = onAboutDismiss
        )
    }

    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(AppTheme.dimens.space_8),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.space_10),
    ) {
        item {
            VerticalSpacerLarge()
            UserCard(
                user = state.uiModel,
                avatarSize = 80.dp,
            )
        }
        item {
            AccountAction(
                imageVector = Icons.Outlined.Email,
                title = stringResource(R.string.label_support),
                onClick = onSupportClick,
            )
            VerticalSpacerMedium()
            AccountAction(
                imageVector = Icons.Outlined.Info,
                title = stringResource(R.string.label_about),
                onClick = onAboutClick,
            )
            VerticalSpacerMedium()
            AccountAction(
                imageVector = Icons.Outlined.Star,
                title = stringResource(R.string.label_rate),
                onClick = onRateUsClick,
            )
            VerticalSpacerMedium()
            AccountAction(
                imageVector = Icons.Outlined.Menu,
                title = stringResource(R.string.label_terms),
                onClick = onTermsClick,
            )
            VerticalSpacerMedium()
            AccountAction(
                imageVector = Icons.Outlined.Lock,
                title = stringResource(R.string.label_privacy),
                onClick = onPrivacyClick,
            )
            VerticalSpacerMedium()
            AccountAction(
                imageVector = Icons.Outlined.ExitToApp,
                title = stringResource(R.string.label_logout),
                onClick = onSignOutClick,
            )
        }
    }
}
