package br.com.jwar.groups.presentation.ui.payment.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.groups.domain.model.PaymentType
import br.com.jwar.groups.presentation.ui.payment.PaymentContract
import br.com.jwar.sharedbill.core.designsystem.components.AppTopBar
import br.com.jwar.sharedbill.core.designsystem.components.CloseNavigationIcon
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.core.designsystem.theme.VerticalSpacerMedium
import br.com.jwar.sharedbill.core.designsystem.theme.paddingMedium
import br.com.jwar.sharedbill.groups.presentation.R
import br.com.jwar.sharedbill.core.designsystem.R as DSR

@Composable
fun PaymentContent(
    params: PaymentContract.PaymentParams,
    onParamsChange: (PaymentContract.PaymentParams) -> Unit = {},
    onSaveClick: () -> Unit = {},
    onNavigateBack: () -> Unit = {}
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        topBar = {
            AppTopBar(
                navigationBack = onNavigateBack,
                navigationIcon = { CloseNavigationIcon(onNavigateBack) },
                title = if (params.paymentType == PaymentType.EXPENSE)
                    stringResource(id = R.string.label_payment_new_expense)
                else stringResource(id = R.string.label_payment_new_settlement),
                actions = {
                    IconButton(onClick = { onSaveClick(); keyboardController?.hide() }) {
                        Icon(Icons.Filled.Done, stringResource(id = DSR.string.description_done))
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Column(
                modifier = Modifier.paddingMedium()
            ) {
                when(params.paymentType) {
                    PaymentType.EXPENSE -> {
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
                    PaymentType.SETTLEMENT -> {
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
        PaymentContent(
            PaymentContract.PaymentParams.sample()
        )
    }
}

