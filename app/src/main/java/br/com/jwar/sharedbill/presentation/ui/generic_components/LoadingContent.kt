@file:OptIn(ExperimentalMaterial3Api::class)

package br.com.jwar.sharedbill.presentation.ui.generic_components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.presentation.ui.theme.SharedBillTheme

@Composable
fun LoadingContent(
    message: String = "Loading"
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
            Text(text = message)
        }
    }
}

@Preview
@Composable
fun PreviewLoadingContent() {
    SharedBillTheme {
        Scaffold {
            LoadingContent()
        }
    }
}