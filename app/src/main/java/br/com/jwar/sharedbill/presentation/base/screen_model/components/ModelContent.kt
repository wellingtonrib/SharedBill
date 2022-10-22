package br.com.jwar.sharedbill.presentation.base.screen_model.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import br.com.jwar.sharedbill.presentation.base.screen_model.ModelContract.State
import br.com.jwar.sharedbill.presentation.ui.generic_components.EmptyContent
import br.com.jwar.sharedbill.presentation.ui.generic_components.ErrorContent
import br.com.jwar.sharedbill.presentation.ui.generic_components.LoadingContent
import br.com.jwar.sharedbill.presentation.ui.theme.fillMaxWidthPaddingMedium

@Composable
@SuppressWarnings
fun ModelContent(
    state: State,
    onClickSomething: () -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxWidthPaddingMedium(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when(state) {
            is State.Loading -> LoadingContent()
            is State.Loaded -> EmptyContent()
            is State.Error -> ErrorContent()
        }
    }
}