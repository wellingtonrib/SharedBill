package br.com.jwar.sharedbill.presentation.ui.screens.group_details.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.R
import br.com.jwar.sharedbill.presentation.ui.generic_components.InfoDialog
import br.com.jwar.sharedbill.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.presentation.models.PaymentUiModel
import br.com.jwar.sharedbill.presentation.ui.theme.*

@Composable
fun GroupPaymentCard(
    payment: PaymentUiModel,
    group: GroupUiModel
) {
    val showingPaymentInfo = PaymentInfoDialog(payment)
    Column {
        Card(modifier = Modifier
            .fillMaxWidth()
            .clickable { showingPaymentInfo.value = true }) {
            Row(
                modifier = Modifier.fillMaxWidthPaddingMedium()
            ) {
                Text(text = payment.createdAt)
                Spacer(modifier = Modifier.horizontalSpaceMedium())
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = payment.description)
                    Text(
                        text = payment.getMessage(group),
                        style = AppTheme.typo.labelSmall
                    )
                }
                Spacer(modifier = Modifier.horizontalSpaceMedium())
                Text(text = payment.value)
            }
        }
        Spacer(modifier = Modifier.verticalSpaceMedium())
    }
}

@Composable
private fun PaymentInfoDialog(payment: PaymentUiModel): MutableState<Boolean> {
    val showingPaymentInfo = remember { mutableStateOf(false) }
    if (showingPaymentInfo.value) {
        InfoDialog(
            image = null,
            title = stringResource(R.string.label_payment_detail),
            message = payment.getInfo(),
            onDismiss = { showingPaymentInfo.value = false },
            onAction = { showingPaymentInfo.value = false }
        )
    }
    return showingPaymentInfo
}

@Preview(showBackground = true)
@Composable
fun PreviewGroupPaymentCard(){
    SharedBillTheme {
        GroupPaymentCard(payment = PaymentUiModel.sample(), group = GroupUiModel.sample())
    }
}