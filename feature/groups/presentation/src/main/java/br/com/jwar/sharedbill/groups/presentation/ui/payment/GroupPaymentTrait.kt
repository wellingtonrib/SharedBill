package br.com.jwar.sharedbill.groups.presentation.ui.payment

import br.com.jwar.sharedbill.core.utility.StringProvider
import br.com.jwar.sharedbill.core.utility.extensions.toCurrency
import br.com.jwar.sharedbill.groups.domain.model.PaymentType
import br.com.jwar.sharedbill.groups.presentation.R
import br.com.jwar.sharedbill.groups.presentation.models.GroupMemberUiModel
import br.com.jwar.sharedbill.groups.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.groups.presentation.models.PaymentUiError
import com.google.common.collect.ImmutableMap
import com.google.common.collect.ImmutableSet
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.Calendar

interface GroupPaymentTrait {

    val stringProvider: StringProvider

    fun getInputFields(groupUiModel: GroupUiModel, paymentType: PaymentType): ImmutableSet<GroupPaymentContract.Field> =
        when (paymentType) {
            PaymentType.EXPENSE -> getExpenseFields(groupUiModel)
            PaymentType.SETTLEMENT -> getSettlementFields(groupUiModel)
            else -> ImmutableSet.of()
        }

    private fun getExpenseFields(groupUiModel: GroupUiModel) = ImmutableSet.of(
        GroupPaymentContract.Field.DescriptionField(
            value = "",
            visible = true,
        ),
        GroupPaymentContract.Field.ValueField(
            value = "",
            visible = true,
        ),
        GroupPaymentContract.Field.DateField(
            value = Calendar.getInstance().timeInMillis,
            visible = true,
        ),
        GroupPaymentContract.Field.PaidByField(
            value = groupUiModel.members.firstOrNull() ?: GroupMemberUiModel(),
            options = groupUiModel.membersToSelect,
            visible = true,
        ),
        GroupPaymentContract.Field.PaidToField(
            value = ImmutableMap.copyOf(groupUiModel.members.associateWith { 1 }),
            options = groupUiModel.members,
            visible = true,
            isMultiSelect = true,
        )
    )

    private fun getSettlementFields(groupUiModel: GroupUiModel) = ImmutableSet.of(
        GroupPaymentContract.Field.DescriptionField(
            value = stringProvider.getString(R.string.label_settlement),
            visible = false,
        ),
        GroupPaymentContract.Field.ValueField(
            value = "",
            visible = true,
        ),
        GroupPaymentContract.Field.DateField(
            value = Calendar.getInstance().timeInMillis,
            visible = false,
        ),
        GroupPaymentContract.Field.PaidByField(
            value = groupUiModel.members.firstOrNull() ?: GroupMemberUiModel(),
            options = groupUiModel.membersToSelect,
            visible = true,
        ),
        GroupPaymentContract.Field.PaidToField(
            value = ImmutableMap.copyOf(groupUiModel.members.drop(1).take(1).associateWith { 1 }),
            options = ImmutableSet.copyOf(groupUiModel.members.drop(1)),
            visible = true,
            isMultiSelect = false,
        )
    )

    fun calculateSharedValue(
        value: String,
        paidTo: ImmutableMap<GroupMemberUiModel, Int>
    ): ImmutableMap<GroupMemberUiModel, String> {
        val valueToShare = value.toBigDecimalOrNull() ?: BigDecimal.ZERO
        val weights = paidTo.values.sum().toBigDecimal().takeIf { it > BigDecimal.ZERO } ?: BigDecimal.ONE
        return ImmutableMap.copyOf(
            paidTo.entries.filter { it.value > 0 }.associate {
                val currentValue = it.value.toBigDecimal()
                val sharedValue = valueToShare.multiply(currentValue).divide(weights, 2, RoundingMode.CEILING)
                it.key to sharedValue.toCurrency()
            }
        )
    }

    fun mapErrorHandler(inputFields: ImmutableSet<GroupPaymentContract.Field>, error: PaymentUiError) =
        inputFields.map { field ->
            if (field::class == error.errorHandler) {
                when (field) {
                    is GroupPaymentContract.Field.DescriptionField -> field.copy(error = error)
                    is GroupPaymentContract.Field.ValueField -> field.copy(error = error)
                    is GroupPaymentContract.Field.DateField -> field.copy(error = error)
                    is GroupPaymentContract.Field.PaidByField -> field.copy(error = error)
                    is GroupPaymentContract.Field.PaidToField -> field.copy(error = error)
                }
            } else {
                field
            }
        }
}
