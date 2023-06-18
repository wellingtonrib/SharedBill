package br.com.jwar.sharedbill.core.designsystem.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import br.com.jwar.sharedbill.core.designsystem.model.UserUiModel
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme

@Composable
fun UserAvatarStack(
    modifier: Modifier = Modifier,
    users: List<UserUiModel>,
    avatarSize: Dp = 48.dp,
    overlap: Dp = 12.dp
) {
    val with = if (users.size > 1) avatarSize.times(users.size) - overlap.times(users.size) else avatarSize
    Box(
        modifier = modifier.width(with),
    ) {
        users.reversed().forEachIndexed { index, user ->
            val overlapPx = with(LocalDensity.current) { overlap.toPx() }
            val offset = index * overlapPx
            UserAvatar(
                modifier = Modifier.offset(x = offset.dp),
                user = user,
                avatarSize = avatarSize,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUserAvatarStack() {
    SharedBillTheme {
        UserAvatarStack(
            users = listOf(
                UserUiModel.sample(),
                UserUiModel.sample(),
            )
        )
    }
}