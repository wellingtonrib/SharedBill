package br.com.jwar.groups.presentation.ui.payment.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.groups.presentation.models.GroupMemberUiModel
import br.com.jwar.groups.presentation.ui.payment.PaymentContract
import br.com.jwar.sharedbill.core.designsystem.theme.HorizontalSpacerMedium
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.core.designsystem.theme.VerticalSpacerSmall
import br.com.jwar.sharedbill.core.designsystem.theme.paddingSmall
import br.com.jwar.sharedbill.groups.domain.model.PaymentType
import br.com.jwar.sharedbill.groups.presentation.R

@Composable
fun PaymentPaidToField(
    params: PaymentContract.PaymentParams,
    onPaymentParamsChange: (PaymentContract.PaymentParams) -> Unit = {}
) {
    var selection by remember { mutableStateOf(params.paidTo) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Column {
            VerticalSpacerSmall()
            Text(text = stringResource(R.string.label_payment_paid_to))
        }
        HorizontalSpacerMedium()
        LazyColumn {
            if (params.paymentType == PaymentType.EXPENSE) {
                items(params.group.members) { member ->
                    CheckboxWitText(
                        member = member,
                        isChecked = selection.contains(member),
                        onCheckedChange = { checked ->
                            selection = if (checked) {
                                selection.toMutableList().apply { add(member) }
                            } else {
                                selection.toMutableList().apply { remove(member) }
                            }
                            onPaymentParamsChange(params.copy(paidTo = selection))
                        }
                    )
                }
            } else {
                items(params.group.members) { member ->
                    RadioButtonWitText(
                        member = member,
                        isChecked = selection.contains(member),
                        onCheckedChange = { checked ->
                            selection = if (checked) listOf(member) else emptyList()
                            onPaymentParamsChange(params.copy(paidTo = selection))
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CheckboxWitText(
    member: GroupMemberUiModel,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    var checkedState by remember { mutableStateOf(isChecked) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                val newValue = !checkedState
                checkedState = newValue
                onCheckedChange(newValue)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            modifier = Modifier,
            checked = checkedState,
            onCheckedChange = { checkedState = it }
        )
        Text(
            text = member.name,
            color = if (checkedState) LocalContentColor.current else Color.Gray
        )
    }
}

@Composable
fun RadioButtonWitText(
    member: GroupMemberUiModel,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .paddingSmall()
            .fillMaxWidth()
            .clickable {
                onCheckedChange(true)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            modifier = Modifier,
            selected = isChecked,
            onClick = null
        )
        HorizontalSpacerMedium()
        Text(
            text = member.name,
            color = if (isChecked) LocalContentColor.current else Color.Gray
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPaymentPaidToField() {
    SharedBillTheme {
        PaymentPaidToField(
            PaymentContract.PaymentParams.sample()
        )
    }
}