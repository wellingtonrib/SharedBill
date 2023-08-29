package br.com.jwar.groups.presentation.ui.payment.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.groups.presentation.models.GroupMemberUiModel
import br.com.jwar.groups.presentation.models.PaymentUiError
import br.com.jwar.sharedbill.core.designsystem.components.SelectDialog
import br.com.jwar.sharedbill.core.designsystem.theme.AppTheme
import br.com.jwar.sharedbill.core.designsystem.theme.HorizontalSpacerMedium
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.groups.presentation.R

@Composable
fun PaymentPaidByField(
    modifier: Modifier = Modifier,
    paidByOptions: Map<GroupMemberUiModel, Boolean> = emptyMap(),
    paidBy: GroupMemberUiModel = GroupMemberUiModel(),
    error: PaymentUiError? = null,
    onValueChange: (GroupMemberUiModel) -> Unit,
) {
    val isPaidBySelecting = remember { mutableStateOf(false) }
    if (isPaidBySelecting.value) {
        SelectDialog(
            title = stringResource(id = R.string.label_payment_paid_by),
            message = stringResource(id = R.string.placeholder_payment_paid_by),
            options = paidByOptions,
            defaultSelection = listOf(paidBy),
            isMultiChoice = false,
            onDismiss = {
                isPaidBySelecting.value = false
            },
            onSelect = {
                isPaidBySelecting.value = false
                onValueChange(it.first())
            }
        )
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top
    ) {
        Text(
            modifier = Modifier.padding(top = AppTheme.dimens.space_4),
            text = stringResource(R.string.label_payment_paid_by)
        )
        HorizontalSpacerMedium()
        Column(modifier = Modifier.weight(1f)) {
            Button(
                onClick = { isPaidBySelecting.value = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = paidBy.name)
            }
            error?.message?.AsText(AppTheme.colors.error)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPaymentPaidByField() {
    SharedBillTheme {
        PaymentPaidByField(
            paidBy = GroupMemberUiModel.sample(),
        ) {

        }
    }
}