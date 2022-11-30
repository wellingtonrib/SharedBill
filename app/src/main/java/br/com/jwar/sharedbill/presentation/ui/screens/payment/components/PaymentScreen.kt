package br.com.jwar.sharedbill.presentation.ui.screens.payment.components

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.presentation.ui.generic_components.LoadingContent
import br.com.jwar.sharedbill.presentation.ui.screens.payment.PaymentContract.SendPaymentParams
import br.com.jwar.sharedbill.presentation.ui.screens.payment.PaymentContract.State
import br.com.jwar.sharedbill.presentation.ui.theme.SharedBillTheme

@Composable
fun PaymentScreen(
    state: State,
    onPaymentParamsChange: (SendPaymentParams) -> Unit = {},
    onSaveClick: () -> Unit = {},
    onNavigateBack: () -> Unit = {},
) {
    when {
        state.isLoading -> LoadingContent()
        state.params != null -> PaymentForm(
            params = state.params,
            onParamsChange = onPaymentParamsChange,
            onSaveClick = onSaveClick,
            onNavigateBack = onNavigateBack
        )
    }
}

@Preview
@Composable
fun PreviewPaymentContent() {
    SharedBillTheme {
        Scaffold {
            PaymentScreen(
                state = State(params = SendPaymentParams.sample()),
            )
        }
    }
}