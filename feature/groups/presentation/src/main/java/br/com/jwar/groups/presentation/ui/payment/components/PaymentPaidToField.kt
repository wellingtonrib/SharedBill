package br.com.jwar.groups.presentation.ui.payment.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.groups.presentation.models.GroupMemberUiModel
import br.com.jwar.groups.presentation.models.PaymentUiError
import br.com.jwar.sharedbill.core.designsystem.components.CheckboxWitText
import br.com.jwar.sharedbill.core.designsystem.components.RadioButtonWitText
import br.com.jwar.sharedbill.core.designsystem.theme.AppTheme
import br.com.jwar.sharedbill.core.designsystem.theme.HorizontalSpacerMedium
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.core.designsystem.theme.VerticalSpacerSmall
import br.com.jwar.sharedbill.core.utility.extensions.toCurrency
import br.com.jwar.sharedbill.groups.presentation.R
import java.math.BigDecimal

@Composable
fun PaymentPaidToField(
    modifier: Modifier = Modifier,
    isExpense: Boolean = true,
    sharedValue: BigDecimal = BigDecimal.ZERO,
    paidToOptions: List<GroupMemberUiModel> = emptyList(),
    paidTo: List<GroupMemberUiModel> = emptyList(),
    error: PaymentUiError? = null,
    onValueChange: (List<GroupMemberUiModel>) -> Unit,
) {
    var selection by remember { mutableStateOf(paidTo) }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Column {
            VerticalSpacerSmall()
            Text(text = stringResource(R.string.label_payment_paid_to))
        }
        HorizontalSpacerMedium()
        LazyColumn {
            if (isExpense) {
                items(paidToOptions) { member ->
                    Row(
                        modifier = modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CheckboxWitText(
                            modifier = Modifier.weight(1f),
                            text = member.name,
                            isChecked = selection.any { it.uid == member.uid },
                            onCheckedChange = { checked ->
                                selection = if (checked) {
                                    selection.toMutableList().apply { add(member) }
                                } else {
                                    selection.toMutableList().apply { remove(member) }
                                }
                                onValueChange(selection)
                            }
                        )
                        Text(
                            modifier = Modifier.padding(end = AppTheme.dimens.space_4),
                            text = if (selection.contains(member)) sharedValue.toCurrency() else "-"
                        )
                    }
                }
            } else {
                items(paidToOptions) { member ->
                    RadioButtonWitText(
                        text = member.name,
                        isChecked = selection.contains(member),
                        onCheckedChange = { checked ->
                            selection = if (checked) listOf(member) else emptyList()
                            onValueChange(selection)
                        }
                    )
                }
            }
            item {
                error?.message?.AsText(AppTheme.colors.error)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPaymentPaidToField() {
    SharedBillTheme {
        PaymentPaidToField(
            sharedValue = BigDecimal.TEN,
            paidTo = listOf(GroupMemberUiModel.sample().copy(uid = "1")),
            paidToOptions = listOf(GroupMemberUiModel.sample().copy(uid = "1")),
        ) {

        }
    }
}