package br.com.jwar.sharedbill.presentation.ui.screens.payment.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.User
import br.com.jwar.sharedbill.presentation.ui.screens.payment.PaymentContract.Event
import br.com.jwar.sharedbill.presentation.ui.screens.payment.PaymentContract.State
import br.com.jwar.sharedbill.presentation.ui.theme.SharedBillTheme
import br.com.jwar.sharedbill.presentation.ui.generic_components.ErrorContent
import br.com.jwar.sharedbill.presentation.ui.generic_components.LoadingContent

@Composable
fun PaymentContent(
    state: State,
    onSendPaymentClick: (Event.SendPaymentParams) -> Unit = {},
    snackHostState: SnackbarHostState = SnackbarHostState(),
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        when(state) {
            is State.Loading -> LoadingContent()
            is State.Editing -> PaymentForm(state.group, state.currentMember, onSendPaymentClick)
            is State.Error -> ErrorContent(state.message)
        }
    }
    SnackbarHost(
        hostState = snackHostState,
        modifier = Modifier.fillMaxWidth().wrapContentHeight(Alignment.Bottom)
    )
}

@Preview
@Composable
fun PreviewPaymentContent() {
    SharedBillTheme {
        Scaffold {
            PaymentContent(state = State.Editing(Group.sample(), User.fake()))
        }
    }
}