package br.com.jwar.sharedbill.core.designsystem.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.jwar.sharedbill.core.designsystem.R
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.core.designsystem.theme.VerticalSpacerMedium
import br.com.jwar.sharedbill.testing.TestTags

@Composable
fun ErrorContent(
    modifier: Modifier = Modifier,
    image: Painter? = painterResource(R.drawable.error_state_default_img),
    message: String = stringResource(id = R.string.message_error_content),
    action: String = stringResource(id = R.string.label_try_again),
    onAction: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .testTag(TestTags.ErrorContent)
            .fillMaxSize()
            .offset(y = (-60).dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if (image != null) {
                Image(
                    modifier = Modifier.size(300.dp),
                    painter = image,
                    contentDescription = stringResource(R.string.description_empty)
                )
            }
            Text(text = message, textAlign = TextAlign.Center)
            VerticalSpacerMedium()
            Button(onClick = { onAction() }) {
                Text(text = action)
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun PreviewErrorContent() {
    SharedBillTheme {
        Scaffold {
            ErrorContent()
        }
    }
}
