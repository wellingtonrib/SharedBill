package br.com.jwar.sharedbill.account.presentation.ui.account.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.account.presentation.R
import br.com.jwar.sharedbill.core.designsystem.theme.AppTheme
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.core.designsystem.theme.paddingMedium

@Composable
fun AccountAction(
    imageVector: ImageVector,
    title: String,
    onClick: () -> Unit = {},
) {
    OutlinedCard {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() },
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier.paddingMedium(),
                imageVector = imageVector,
                contentDescription = null
            )
            Text(
                modifier =  Modifier.weight(1f),
                text = title,
                style = AppTheme.typo.titleMedium
            )
            Icon(
                modifier = Modifier.paddingMedium(),
                imageVector = ImageVector.vectorResource(R.drawable.ic_chevron_right),
                contentDescription = null
            )
        }
    }
}


@Composable
@Preview
fun PreviewAccountAction() {
    SharedBillTheme {
        AccountAction(
            imageVector = Icons.Outlined.Info,
            title = "Info",
        )
    }
}