package br.com.jwar.sharedbill.core.designsystem.components

import androidx.annotation.ColorInt
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.ColorUtils
import br.com.jwar.sharedbill.core.designsystem.R
import br.com.jwar.sharedbill.core.designsystem.model.UserUiModel
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import kotlin.math.absoluteValue

@Composable
fun UserAvatar(
    modifier: Modifier = Modifier,
    user: UserUiModel,
    avatarSize: Dp = 48.dp,
    borderColor: Color = Color.White
) {
    SubcomposeAsyncImage(
        modifier = modifier
            .border(1.dp, borderColor, CircleShape)
            .size(avatarSize)
            .clip(CircleShape),
        model = user.imageUrl,
        contentDescription = stringResource(R.string.description_user_avatar)
    ) {
        val state = painter.state
        if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
            UserAvatarPlaceholder(user.id, user.name, avatarSize)
        } else {
            SubcomposeAsyncImageContent()
        }
    }
}

@Composable
private fun UserAvatarPlaceholder(
    userId: String,
    userName: String,
    avatarSize: Dp = 48.dp
) {
    val color = remember(userId, userName) {
        Color("$userId / $userName".toHslColor())
    }
    Box(
        modifier = Modifier
            .size(avatarSize)
            .clip(CircleShape)
            .background(color),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.padding(1.dp),
            text = userName.initials(),
            style = TextStyle(color = Color.White, fontSize = avatarSize.toSp())
        )
    }
}

fun String.initials() = this.split(" ").let {
    (it.first().take(1) + it.last().take(1)).uppercase()
}

@ColorInt
fun String.toHslColor(saturation: Float = 0.5f, lightness: Float = 0.4f): Int {
    val hue = fold(0) { acc, char -> char.code + acc * 37 } % 360
    return ColorUtils.HSLToColor(floatArrayOf(hue.absoluteValue.toFloat(), saturation, lightness))
}

@Composable
fun Dp.toSp(): TextUnit {
    val fontScale = LocalConfiguration.current.fontScale
    val scaledDensity = LocalContext.current.resources.displayMetrics.scaledDensity
    return (value / scaledDensity / fontScale).sp
}

@Preview(showBackground = true)
@Composable
fun PreviewUserAvatar() {
    SharedBillTheme {
        UserAvatar(
            user = UserUiModel.sample(),
        )
    }
}