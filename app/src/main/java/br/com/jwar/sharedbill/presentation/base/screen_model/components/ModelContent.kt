package br.com.jwar.sharedbill.presentation.base.screen_model.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.jwar.sharedbill.presentation.base.screen_model.ModelContract.State
import br.com.jwar.sharedbill.presentation.ui.widgets.EmptyContent
import br.com.jwar.sharedbill.presentation.ui.widgets.ErrorContent
import br.com.jwar.sharedbill.presentation.ui.widgets.LoadingContent

@Composable
fun ModelContent(
    state: State,
    onClickSomething: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when(state) {
            is State.Loading -> LoadingContent()
            is State.Loaded -> EmptyContent()
            is State.Error -> ErrorContent()
        }
    }
}