package br.com.jwar.sharedbill.presentation.base.screen_model.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import br.com.jwar.sharedbill.presentation.base.screen_model.ModelContract.State
import br.com.jwar.sharedbill.core.designsystem.components.EmptyContent
import br.com.jwar.sharedbill.core.designsystem.components.ErrorContent
import br.com.jwar.sharedbill.core.designsystem.components.LoadingContent
import br.com.jwar.sharedbill.core.designsystem.theme.fillMaxWidthPaddingMedium

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
}//todo move base classes to appropriate module