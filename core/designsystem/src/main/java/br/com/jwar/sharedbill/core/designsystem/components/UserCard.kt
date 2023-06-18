package br.com.jwar.sharedbill.core.designsystem.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.jwar.sharedbill.core.designsystem.theme.AppTheme
import br.com.jwar.sharedbill.core.designsystem.theme.Icons
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.core.designsystem.theme.sizeLarge
import br.com.jwar.sharedbill.core.designsystem.util.forwardingPainter
import coil.compose.AsyncImage
import coil.request.ImageRequest

class UserCardUiModel(
    val name: String = "",
    val email: String = "",
    val imageUrl: String = "",
) {
    companion object {
        fun sample() = UserCardUiModel(
            name = "User One",
            email = "user@email.com",
            imageUrl = "",
        )
    }
}

@Composable
fun UserCard(user: UserCardUiModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UserImage(user.imageUrl)
        Text(text = user.name, style = AppTheme.typo.titleLarge)
        Text(text = user.email, style = AppTheme.typo.bodyMedium)
    }
}

@Composable
fun UserImage(
    imageUrl: String,
    bordered: Boolean = true
) {
    val placeholder = forwardingPainter(
        painter = painterResource(Icons.Member),
        colorFilter = ColorFilter.tint(AppTheme.colors.primary)
    )
    var modifier = Modifier.sizeLarge().clip(CircleShape)

    if (bordered) {
        modifier = modifier.then(Modifier.border(2.dp, AppTheme.colors.primary, CircleShape))
    }

    AsyncImage(
        modifier = modifier,
        contentScale = ContentScale.Crop,
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        placeholder = placeholder,
        error = placeholder,
        contentDescription = "",
    )
}

@Preview(showBackground = true)
@Composable
fun Preview_() {
    SharedBillTheme {
        UserCard(user = UserCardUiModel.sample())
    }
}