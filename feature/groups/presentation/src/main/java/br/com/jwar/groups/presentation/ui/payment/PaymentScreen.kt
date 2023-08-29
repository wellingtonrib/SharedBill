package br.com.jwar.groups.presentation.ui.payment

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.groups.presentation.models.PaymentUiModel
import br.com.jwar.groups.presentation.ui.payment.components.PaymentContent
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.groups.domain.model.PaymentType

@Composable
fun PaymentScreen(
    state: PaymentContract.State,
    paymentType: PaymentType = PaymentType.EXPENSE,
    onSaveClick: (payment: PaymentUiModel) -> Unit,
    onNavigateBack: () -> Unit = {},
) {
    PaymentContent(
        state = state,
        onSaveClick = onSaveClick,
        onNavigateBack = onNavigateBack
    )
}

@Preview
@Composable
fun PreviewPaymentContent() {
    SharedBillTheme {
        PaymentScreen(
            state = PaymentContract.State(
                inputFields = emptyList()
            ),
            onSaveClick = {},
        )
    }
}