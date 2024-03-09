package br.com.jwar.sharedbill.core.designsystem.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import br.com.jwar.sharedbill.core.designsystem.model.UserUiModel
import br.com.jwar.sharedbill.core.designsystem.theme.AppTheme
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme

@Composable
fun UserCard(
    modifier: Modifier = Modifier,
    avatarSize: Dp = 62.dp,
    user: UserUiModel,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UserAvatar(user = user, avatarSize = avatarSize)
        Text(text = user.name, style = AppTheme.typo.titleLarge)
        if (user.email.isNotBlank()) {
            Text(text = user.email, style = AppTheme.typo.bodyMedium)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUserCard() {
    SharedBillTheme {
        UserCard(user = UserUiModel.sample())
    }
}
