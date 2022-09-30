@file:OptIn(ExperimentalMaterial3Api::class)

package br.com.jwar.sharedbill.presentation.ui.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.presentation.ui.theme.SharedBillTheme

@Composable
fun ErrorContent(
    message: String = "An error occurred",
    action: () -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = message)
            Button(onClick = { action() }) {
                Text(text = "Try Again")
            }
        }
    }
}

@Preview
@Composable
fun PreviewErrorContent() {
    SharedBillTheme {
        Scaffold {
            ErrorContent()
        }
    }
}