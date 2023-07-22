package br.com.jwar.sharedbill.account.presentation.screens.account.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.jwar.sharedbill.account.presentation.R
import br.com.jwar.sharedbill.account.presentation.screens.account.AccountContract
import br.com.jwar.sharedbill.core.designsystem.components.UserCard
import br.com.jwar.sharedbill.core.designsystem.theme.AppTheme
import br.com.jwar.sharedbill.core.designsystem.theme.VerticalSpacerMedium
import br.com.jwar.sharedbill.core.designsystem.theme.paddingMedium

@Composable
fun AccountForm(
    state: AccountContract.State.Loaded,
    onSignOutClick: () -> Unit
) {
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(AppTheme.dimens.space_8),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.space_10),
    ) {
        item {
            VerticalSpacerMedium()
            UserCard(
                user = state.uiModel,
                avatarSize = 80.dp,
            )
        }
        item {
            AccountAction(
                imageVector = Icons.Outlined.Email,
                title = stringResource(R.string.label_support)
            )
            VerticalSpacerMedium()
            AccountAction(
                imageVector = Icons.Outlined.Info,
                title = stringResource(R.string.label_about)
            )
            VerticalSpacerMedium()
            AccountAction(
                imageVector = Icons.Outlined.Menu,
                title = stringResource(R.string.label_terms)
            )
            VerticalSpacerMedium()
            AccountAction(
                imageVector = Icons.Outlined.Lock,
                title = stringResource(R.string.label_privacy)
            )
            VerticalSpacerMedium()
            SignOutButton(onSignOutClick)
        }
    }
}

@Composable
private fun AccountAction(
    imageVector: ImageVector,
    title: String,
    onClick: () -> Unit = {},
) {
    Card {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() },
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier.paddingMedium(),
                imageVector = imageVector,
                contentDescription = null
            )
            Text(text = title)
        }
    }
}