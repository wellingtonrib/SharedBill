package br.com.jwar.sharedbill.core.designsystem.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.jwar.sharedbill.core.designsystem.model.UserUiModel
import br.com.jwar.sharedbill.core.designsystem.theme.AppTheme
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme

@Composable
fun UserCard(user: UserUiModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UserAvatar(user = user, avatarSize = 62.dp)
        Text(text = user.name, style = AppTheme.typo.titleLarge)
        if (user.email.isNotBlank()) {
            Text(text = user.email, style = AppTheme.typo.bodyMedium)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview_() {
    SharedBillTheme {
        UserCard(user = UserUiModel.sample())
    }
}