package br.com.jwar.sharedbill.groups.presentation.ui.group_payment.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.core.designsystem.components.WeightWitText
import br.com.jwar.sharedbill.core.designsystem.components.Field
import br.com.jwar.sharedbill.core.designsystem.components.RadioButtonWitText
import br.com.jwar.sharedbill.core.designsystem.theme.AppTheme
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.groups.presentation.R
import br.com.jwar.sharedbill.groups.presentation.models.GroupMemberUiModel
import br.com.jwar.sharedbill.groups.presentation.models.PaymentUiError
import com.google.common.collect.ImmutableMap
import com.google.common.collect.ImmutableSet

@Composable
fun PaymentPaidToField(
    modifier: Modifier = Modifier,
    isMultiSelect: Boolean = true,
    sharedValue: ImmutableMap<GroupMemberUiModel, String> = ImmutableMap.of(),
    options: ImmutableSet<GroupMemberUiModel> = ImmutableSet.of(),
    value: ImmutableMap<GroupMemberUiModel, Int> = ImmutableMap.of(),
    error: PaymentUiError? = null,
    onValueChange: (ImmutableMap<GroupMemberUiModel, Int>) -> Unit,
) {
    Field {

        Column(
            modifier = modifier.fillMaxWidth(),
        ) {
            Text(
                modifier = Modifier.padding(top = AppTheme.dimens.space_4),
                text = if (isMultiSelect) {
                    stringResource(R.string.label_payment_divided_by)
                } else {
                    stringResource(R.string.label_payment_paid_to)
                }
            )
            LazyColumn(
                modifier = Modifier
                    .padding(start = AppTheme.dimens.space_4, top = AppTheme.dimens.space_4),
            ) {
                if (isMultiSelect) {
                    items(options.asList()) { member ->
                        Row(
                            modifier = modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            WeightWitText(
                                modifier = Modifier.weight(1f),
                                text = member.name,
                                weight = value[member] ?: 0,
                                onWeightChange = { weight ->
                                    val newValue = ImmutableMap.copyOf(value.toMutableMap().apply {
                                        this[member] = weight
                                    })
                                    onValueChange(newValue)
                                }
                            )
                            Text(
                                modifier = Modifier.padding(end = AppTheme.dimens.space_4),
                                text = sharedValue[member] ?: "-"
                            )
                        }
                    }
                } else {
                    items(options.asList()) { member ->
                        RadioButtonWitText(
                            text = member.name,
                            isChecked = value.contains(member),
                            onCheckedChange = { checked ->
                                val newValue = if (checked) {
                                    ImmutableMap.of(member, 1)
                                } else {
                                    ImmutableMap.of()
                                }
                                onValueChange(newValue)
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
}

@Preview(showBackground = true)
@Composable
fun PreviewPaymentPaidToField() {
    SharedBillTheme {
        PaymentPaidToField(
            sharedValue = ImmutableMap.of(
                GroupMemberUiModel.sample().copy(uid = "1"), "1"
            ),
            value = ImmutableMap.of(
                GroupMemberUiModel.sample().copy(uid = "1"), 1
            ),
            options = ImmutableSet.of(
                GroupMemberUiModel.sample().copy(uid = "1"),
                GroupMemberUiModel.sample(),
                GroupMemberUiModel.sample(),
            ),
        ) {

        }
    }
}