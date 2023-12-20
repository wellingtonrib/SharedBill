package br.com.jwar.sharedbill.groups.presentation.ui.payment.components

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
import br.com.jwar.sharedbill.core.designsystem.components.CheckboxWitText
import br.com.jwar.sharedbill.core.designsystem.components.Field
import br.com.jwar.sharedbill.core.designsystem.components.RadioButtonWitText
import br.com.jwar.sharedbill.core.designsystem.theme.AppTheme
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.groups.presentation.R
import br.com.jwar.sharedbill.groups.presentation.models.GroupMemberUiModel
import br.com.jwar.sharedbill.groups.presentation.models.PaymentUiError
import com.google.common.collect.ImmutableSet

@Composable
fun PaymentPaidToField(
    modifier: Modifier = Modifier,
    isMultiSelect: Boolean = true,
    sharedValue: String = "",
    options: ImmutableSet<GroupMemberUiModel> = ImmutableSet.of(),
    value: ImmutableSet<GroupMemberUiModel> = ImmutableSet.of(),
    error: PaymentUiError? = null,
    onValueChange: (ImmutableSet<GroupMemberUiModel>) -> Unit,
) {
    Field {

        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Text(
                modifier = Modifier.padding(top = AppTheme.dimens.space_4),
                text = stringResource(R.string.label_payment_paid_to)
            )
            LazyColumn {
                if (isMultiSelect) {
                    items(options.asList()) { member ->
                        Row(
                            modifier = modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CheckboxWitText(
                                modifier = Modifier.weight(1f),
                                text = member.name,
                                isChecked = value.any { it.uid == member.uid },
                                onCheckedChange = { checked ->
                                    val newValue = if (checked) {
                                        ImmutableSet.copyOf(value.toMutableList().apply { add(member) })
                                    } else {
                                        ImmutableSet.copyOf(value.toMutableList().apply { remove(member) })
                                    }
                                    onValueChange(newValue)
                                }
                            )
                            Text(
                                modifier = Modifier.padding(end = AppTheme.dimens.space_4),
                                text = if (value.contains(member)) sharedValue else "-"
                            )
                        }
                    }
                } else {
                    items(options.asList()) { member ->
                        RadioButtonWitText(
                            text = member.name,
                            isChecked = value.contains(member),
                            onCheckedChange = { checked ->
                                val newValue = if (checked) ImmutableSet.of(member) else ImmutableSet.of()
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
            sharedValue = "",
            value = ImmutableSet.of(
                GroupMemberUiModel.sample().copy(uid = "1")
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