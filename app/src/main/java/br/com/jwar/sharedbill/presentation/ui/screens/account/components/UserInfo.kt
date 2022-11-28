package br.com.jwar.sharedbill.presentation.ui.screens.account.components

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.jwar.sharedbill.R
import br.com.jwar.sharedbill.presentation.models.UserUiModel
import br.com.jwar.sharedbill.presentation.ui.theme.AppTheme
import br.com.jwar.sharedbill.presentation.ui.theme.SharedBillTheme
import br.com.jwar.sharedbill.presentation.ui.theme.sizeLarge
import br.com.jwar.sharedbill.presentation.ui.util.forwardingPainter
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun UserInfo(user: UserUiModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val placeholder = forwardingPainter(
            painter = painterResource(R.drawable.ic_baseline_person_24),
            colorFilter = ColorFilter.tint(AppTheme.colors.primary)
        )
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(user.imageUrl)
                .crossfade(true)
                .build(),
            placeholder = placeholder,
            error = placeholder,
            contentDescription = stringResource(id = R.string.description_user_image),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .sizeLarge()
                .clip(CircleShape)
                .border(2.dp, AppTheme.colors.primary, CircleShape)
        )
        Text(text = user.name, style = AppTheme.typo.titleLarge)
        Text(text = user.email, style = AppTheme.typo.bodyMedium)
    }
}

@Preview(showBackground = true)
@Composable
fun Preview_() {
    SharedBillTheme {
        UserInfo(user = UserUiModel.sample())
    }
}