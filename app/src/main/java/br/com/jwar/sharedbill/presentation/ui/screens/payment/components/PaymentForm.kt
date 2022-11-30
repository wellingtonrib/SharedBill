package br.com.jwar.sharedbill.presentation.ui.screens.payment.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.R
import br.com.jwar.sharedbill.presentation.navigation.AppTopBar
import br.com.jwar.sharedbill.presentation.navigation.CloseNavigationIcon
import br.com.jwar.sharedbill.presentation.ui.screens.payment.PaymentContract
import br.com.jwar.sharedbill.presentation.ui.theme.SharedBillTheme
import br.com.jwar.sharedbill.presentation.ui.theme.VerticalSpacerMedium
import br.com.jwar.sharedbill.presentation.ui.theme.paddingMedium

@Composable
fun PaymentForm(
    params: PaymentContract.SendPaymentParams,
    onParamsChange: (PaymentContract.SendPaymentParams) -> Unit = {},
    onSaveClick: () -> Unit = {},
    onNavigateBack: () -> Unit = {}
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        topBar = {
            AppTopBar(
                navigationBack = onNavigateBack,
                navigationIcon = { CloseNavigationIcon(onNavigateBack) },
                title = stringResource(id = R.string.label_payment_new),
                actions = {
                    IconButton(onClick = { onSaveClick(); keyboardController?.hide() }) {
                        Icon(Icons.Filled.Done, stringResource(id = R.string.description_done))
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier.paddingMedium()
        ) {
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
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPaymentForm() {
    SharedBillTheme {
        PaymentForm(
            PaymentContract.SendPaymentParams.sample(),
        )
    }
}

