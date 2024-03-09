package br.com.jwar.sharedbill.core.designsystem.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import br.com.jwar.sharedbill.core.designsystem.R
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.core.designsystem.theme.VerticalSpacerMedium
import br.com.jwar.sharedbill.core.designsystem.theme.fillMaxWidthPaddingMedium
import br.com.jwar.sharedbill.core.designsystem.theme.sizeMedium

@Composable
fun InfoDialog(
    @DrawableRes image: Int? = R.drawable.ic_baseline_info_24,
    title: String,
    message: String,
    messageAlign: TextAlign = TextAlign.Center,
    action: String = stringResource(id = R.string.label_ok),
    onDismiss: () -> Unit,
    onAction: () -> Unit
) {
    Dialog(
        onDismissRequest = { onDismiss() },
    ) {
        InfoDialogContent(
            image = image,
            title = title,
            message = message,
            messageAlign = messageAlign,
            action = action,
            onAction = onAction
        )
    }
}

@Composable
private fun InfoDialogContent(
    image: Int?,
    title: String,
    message: String,
    messageAlign: TextAlign = TextAlign.Center,
    action: String,
    onAction: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
    ) {
        Column(
            modifier = Modifier.fillMaxWidthPaddingMedium(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (image != null) {
                Image(
                    modifier = Modifier.sizeMedium(),
                    painter = painterResource(image),
                    contentDescription = null
                )
            }
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge
            )
            VerticalSpacerMedium()
            Text(
                text = message,
                textAlign = messageAlign,
                style = LocalTextStyle.current.copy(textDirection = TextDirection.Content)
            )
            VerticalSpacerMedium()
            Button(
                onClick = { onAction() }
            ) {
                Text(text = action)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewInfoDialogContent() {
    SharedBillTheme {
        InfoDialogContent(
            image = R.drawable.ic_baseline_info_24,
            title = "Title",
            message = "My message description",
            action = "Ok"
        ) {}
    }
}
