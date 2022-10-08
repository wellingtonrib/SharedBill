package br.com.jwar.sharedbill.presentation.ui.generic_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import br.com.jwar.sharedbill.R
import br.com.jwar.sharedbill.presentation.ui.theme.SharedBillTheme

@Composable
fun InfoDialog(
    image: Int? = R.drawable.ic_baseline_info_24,
    title: String,
    message: String,
    action: String = "Ok",
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
    action: String,
    onAction: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (image != null) {
                Image(
                    modifier = Modifier.size(100.dp),
                    painter = painterResource(image),
                    contentDescription = "Image Info"
                )
            }
            Text(text = title, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = message)
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { onAction() }
            ) {
                Text(text = action)
            }
        }
    }
}

@Preview
@Composable
fun PreviewInfoDialogContent() {
    SharedBillTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(20.dp),
            contentAlignment = Alignment.Center,
        ) {
            InfoDialogContent(
                image = R.drawable.ic_baseline_info_24,
                title = "Title",
                message = "My message description",
                action = "Ok"
            ) {

            }
        }
    }
}