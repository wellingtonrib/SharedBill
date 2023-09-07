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
import br.com.jwar.sharedbill.core.designsystem.util.LogCompositions
import br.com.jwar.sharedbill.groups.presentation.R
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
    LogCompositions("PaymentContent PaymentPaidToField")

    var selection by remember { mutableStateOf(value) }

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
            if (isMultiSelect) {
                items(options.asList()) { member ->
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
                                    ImmutableSet.copyOf(selection.toMutableList().apply { add(member) })
                                } else {
                                    ImmutableSet.copyOf(selection.toMutableList().apply { remove(member) })
                                }
                                onValueChange(selection)
                            }
                        )
                        Text(
                            modifier = Modifier.padding(end = AppTheme.dimens.space_4),
                            text = if (selection.contains(member)) sharedValue else "-"
                        )
                    }
                }
            } else {
                items(options.asList()) { member ->
                    RadioButtonWitText(
                        text = member.name,
                        isChecked = selection.contains(member),
                        onCheckedChange = { checked ->
                            selection = if (checked) ImmutableSet.of(member) else ImmutableSet.of()
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
            sharedValue = "",
            value = ImmutableSet.of(GroupMemberUiModel.sample().copy(uid = "1")),
            options = ImmutableSet.of(GroupMemberUiModel.sample().copy(uid = "1")),
        ) {

        }
    }
}