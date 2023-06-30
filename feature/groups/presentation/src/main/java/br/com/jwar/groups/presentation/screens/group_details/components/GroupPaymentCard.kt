package br.com.jwar.groups.presentation.screens.group_details.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.groups.presentation.models.GroupUiModel
import br.com.jwar.groups.presentation.models.PaymentUiModel
import br.com.jwar.sharedbill.core.designsystem.components.InfoDialog
import br.com.jwar.sharedbill.core.designsystem.theme.AppTheme
import br.com.jwar.sharedbill.core.designsystem.theme.HorizontalSpacerMedium
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.core.designsystem.theme.VerticalSpacerMedium
import br.com.jwar.sharedbill.core.designsystem.theme.fillMaxWidthPaddingMedium
import br.com.jwar.sharedbill.groups.presentation.R

@Composable
fun GroupPaymentCard(
    payment: PaymentUiModel,
    group: GroupUiModel
) {
    val showingPaymentInfo = PaymentInfoDialog(payment)
    Column {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showingPaymentInfo.value = true },

        ) {
            Row(
                modifier = Modifier.fillMaxWidthPaddingMedium()
            ) {
                Text(text = payment.createdAt)
                HorizontalSpacerMedium()
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = payment.description)
                    Text(
                        text = payment.getMessage(group),
                        style = AppTheme.typo.labelSmall
                    )
                }
                HorizontalSpacerMedium()
                Text(text = payment.value)
            }
        }
        VerticalSpacerMedium()
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