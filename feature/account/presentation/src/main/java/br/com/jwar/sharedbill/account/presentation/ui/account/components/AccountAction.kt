package br.com.jwar.sharedbill.account.presentation.ui.account.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import br.com.jwar.sharedbill.core.designsystem.theme.paddingMedium

@Composable
fun AccountAction(
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