package br.com.jwar.sharedbill.groups.presentation.ui.group_details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.core.designsystem.components.InfoDialog
import br.com.jwar.sharedbill.core.designsystem.theme.AppTheme
import br.com.jwar.sharedbill.core.designsystem.theme.HorizontalSpacerMedium
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.core.designsystem.theme.paddingMedium
import br.com.jwar.sharedbill.core.utility.extensions.DATE_FORMAT_DEFAULT
import br.com.jwar.sharedbill.core.utility.extensions.format
import br.com.jwar.sharedbill.groups.presentation.R
import br.com.jwar.sharedbill.groups.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.groups.presentation.models.PaymentUiModel

@Composable
fun GroupPaymentCard(
    payment: PaymentUiModel,
    group: GroupUiModel
) {
    val showingPaymentInfo = paymentInfoDialog(payment)

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = { showingPaymentInfo.value = true }
    ) {
        Row(
            modifier = Modifier.paddingMedium()
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = payment.description,
                    style = AppTheme.typo.titleMedium
                )
                Text(
                    text = payment.getMessage(group),
                    style = AppTheme.typo.bodySmall
                )
            }
            HorizontalSpacerMedium()
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = payment.value,
                    style = AppTheme.typo.titleMedium
                )
                Text(
                    text = payment.createdAt.format(DATE_FORMAT_DEFAULT),
                    style = AppTheme.typo.bodySmall
                )
            }
        }
    }
}

@Composable
private fun paymentInfoDialog(payment: PaymentUiModel): MutableState<Boolean> {
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

@Composable
private fun PaymentUiModel.getInfo() =
    StringBuilder().apply {
        appendLine(
            stringResource(
                R.string.message_payment_description,
                description,
                value,
                paidBy.firstName,
                paidTo.joinToString { it.firstName },
                createdAt.format("dd.MM.yy HH:mm")
            )
        )
        if (paidBy.uid != createdBy.uid) {
            appendLine(stringResource(R.string.message_payment_created_by, createdBy.firstName))
        }
    }.toString()

@Composable
fun PaymentUiModel.getMessage(group: GroupUiModel) =
    if (paidTo.size == group.members.size) {
        stringResource(R.string.message_payment_detail_to_all, paidBy.firstName)
    } else {
        stringResource(R.string.message_payment_detail, paidBy.firstName, paidTo.joinToString(limit = 3, truncated = "...") { it.firstName })
    }

@Preview(showBackground = true)
@Composable
fun PreviewGroupPaymentCard(){
    SharedBillTheme {
        GroupPaymentCard(payment = PaymentUiModel.sample(), group = GroupUiModel.sample())
    }
}