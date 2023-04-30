package br.com.jwar.groups.presentation.screens.payment.components

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.groups.presentation.screens.payment.PaymentContract
import br.com.jwar.sharedbill.core.designsystem.components.LoadingContent
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme

@Composable
fun PaymentScreen(
    state: PaymentContract.State,
    onPaymentParamsChange: (PaymentContract.PaymentParams) -> Unit = {},
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
                state = PaymentContract.State(params = PaymentContract.PaymentParams.sample()),
            )
        }
    }
}