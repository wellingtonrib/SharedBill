package br.com.jwar.sharedbill.presentation.ui.screens.payment.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.presentation.ui.generic_components.LoadingContent
import br.com.jwar.sharedbill.presentation.ui.screens.payment.PaymentContract.SendPaymentParams
import br.com.jwar.sharedbill.presentation.ui.screens.payment.PaymentContract.State
import br.com.jwar.sharedbill.presentation.ui.theme.SharedBillTheme
import br.com.jwar.sharedbill.presentation.ui.theme.fillMaxWidthPaddingMedium

@Composable
fun PaymentContent(
    state: State,
    onPaymentParamsChange: (SendPaymentParams) -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxWidthPaddingMedium()
    ) {
        when {
            state.isLoading -> LoadingContent()
            state.params != null -> PaymentForm(state.params, onPaymentParamsChange)
        }
    }
}

@Preview
@Composable
fun PreviewPaymentContent() {
    SharedBillTheme {
        Scaffold {
            PaymentContent(
                state = State(params = SendPaymentParams.sample())
            )
        }
    }
}