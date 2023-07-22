package br.com.jwar.groups.presentation.screens.payment.components

import FormTopBar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.groups.presentation.models.PaymentType
import br.com.jwar.groups.presentation.screens.payment.PaymentContract
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.core.designsystem.theme.VerticalSpacerMedium
import br.com.jwar.sharedbill.core.designsystem.theme.paddingMedium
import br.com.jwar.sharedbill.groups.presentation.R

@Composable
fun PaymentForm(
    params: PaymentContract.PaymentParams,
    onParamsChange: (PaymentContract.PaymentParams) -> Unit = {},
    onSaveClick: () -> Unit = {},
    onNavigateBack: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            FormTopBar(
                onNavigateBack = { onNavigateBack() },
                title = if (params.paymentType == PaymentType.Expense)
                    stringResource(id = R.string.label_payment_new_expense)
                else stringResource(id = R.string.label_payment_new_settlement),
                onSaveClick = { onSaveClick() }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Column(
                modifier = Modifier.paddingMedium()
            ) {
                when(params.paymentType) {
                    PaymentType.Expense -> {
                        PaymentDescriptionField(params, onParamsChange)
                        VerticalSpacerMedium()
                        PaymentValueField(params, onParamsChange)
                        VerticalSpacerMedium()
                        PaymentDateField(params, onParamsChange)
                        VerticalSpacerMedium()
                        PaymentPaidByField(params, onParamsChange)
                        VerticalSpacerMedium()
                        PaymentPaidToField(params, onParamsChange)
                    }
                    PaymentType.Settlement -> {
                        PaymentValueField(params, onParamsChange)
                        VerticalSpacerMedium()
                        PaymentPaidByField(params, onParamsChange)
                        VerticalSpacerMedium()
                        PaymentPaidToField(params, onParamsChange)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPaymentForm() {
    SharedBillTheme {
        PaymentForm(
            PaymentContract.PaymentParams.sample()
        )
    }
}

