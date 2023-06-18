package br.com.jwar.sharedbill.core.designsystem.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.core.designsystem.R
import br.com.jwar.sharedbill.core.designsystem.theme.AppTheme
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.core.designsystem.theme.VerticalSpacerMedium

@Composable
fun EmptyContent(
    message: String = stringResource(id = R.string.message_empty_content),
    action: String? = null,
    onAction: (() -> Unit)? = null
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center,){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            VerticalSpacerMedium()
            Text(
                textAlign = TextAlign.Center,
                text = message,
                color = Color.LightGray,
                style = AppTheme.typo.titleLarge
            )
            if (onAction != null && action != null) {
                Button(onClick = { onAction() }) {
                    Text(text = action)
                }
            }
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun PreviewEmptyContent() {
    SharedBillTheme {
        Scaffold {
            EmptyContent()
        }
    }
}