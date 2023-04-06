package br.com.jwar.groups.presentation.screens.group_details.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.com.jwar.sharedbill.core.designsystem.theme.AppTheme
import br.com.jwar.sharedbill.groups.presentation.R

@Composable
fun GroupPaymentsEmpty(modifier: Modifier) {
    Box(
        modifier = modifier.then(Modifier.fillMaxWidth().padding(bottom = 80.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = stringResource(id = R.string.message_no_expenses),
            color = Color.LightGray,
            style = AppTheme.typo.titleLarge
        )
    }
}