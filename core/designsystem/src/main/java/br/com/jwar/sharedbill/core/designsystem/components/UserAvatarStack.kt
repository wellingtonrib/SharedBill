package br.com.jwar.sharedbill.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import br.com.jwar.sharedbill.core.designsystem.model.UserUiModel
import br.com.jwar.sharedbill.core.designsystem.theme.AppTheme
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme

sealed class AvatarItem {
    data class UserAvatar(val user: UserUiModel) : AvatarItem()
    data class NotShowingIndicator(val count: Int) : AvatarItem()
}

@Composable
fun UserAvatarStack(
    modifier: Modifier = Modifier,
    users: List<UserUiModel>,
    avatarSize: Dp = 48.dp,
    overlap: Dp = 12.dp,
    maxAvatars: Int = 3
) {
    val avatarItems = users
        .take(maxAvatars)
        .map { AvatarItem.UserAvatar(it) }
        .toMutableList<AvatarItem>()
        .apply {
            val usersNotShowing = users.size - this.size
            if (usersNotShowing > 0) {
                add(AvatarItem.NotShowingIndicator(usersNotShowing))
            }
        }
    val with = if (avatarItems.size > 1) avatarSize.times(avatarItems.size) - overlap.times(avatarItems.size) else avatarSize
    val overlapPx = with(LocalDensity.current) { overlap.toPx() }

    Box(
        modifier = modifier.width(with),
    ) {
        avatarItems.forEachIndexed { index, item ->
            val offset = index * overlapPx
            when(item) {
                is AvatarItem.UserAvatar ->
                    UserAvatar(
                        modifier = Modifier.offset(x = offset.dp),
                        avatarSize = avatarSize,
                        user = item.user,
                    )
                is AvatarItem.NotShowingIndicator ->
                    NotShowingIndicator(
                        modifier = Modifier.offset(x = offset.dp),
                        avatarSize = avatarSize,
                        indicator = item.count
                    )
            }
        }
    }
}

@Composable
private fun NotShowingIndicator(
    modifier: Modifier,
    avatarSize: Dp,
    indicator: Int
) {
    Box(
        modifier = modifier
            .size(avatarSize)
            .border(width = 1.dp, color = Color.White, shape = CircleShape)
            .background(
                color = Color.LightGray,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "+$indicator",
            color = Color.White,
            style = AppTheme.typo.labelMedium
        )
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
                UserUiModel.sample(),
                UserUiModel.sample(),
                UserUiModel.sample(),
                UserUiModel.sample(),
                UserUiModel.sample(),
            )
        )
    }
}