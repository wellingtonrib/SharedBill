package br.com.jwar.sharedbill.groups.presentation.ui.payment

import br.com.jwar.sharedbill.groups.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.groups.presentation.models.PaymentUiError
import br.com.jwar.sharedbill.core.utility.StringProvider
import br.com.jwar.sharedbill.core.utility.extensions.toCurrency
import br.com.jwar.sharedbill.groups.domain.model.PaymentType
import br.com.jwar.sharedbill.groups.presentation.R
import br.com.jwar.sharedbill.groups.presentation.models.GroupMemberUiModel
import com.google.common.collect.ImmutableSet
import java.math.BigDecimal
import java.util.Calendar

interface PaymentTrait {

    val stringProvider: StringProvider

    fun getInputFields(groupUiModel: GroupUiModel, paymentType: PaymentType): ImmutableSet<PaymentContract.Field> =
        when (paymentType) {
            PaymentType.EXPENSE -> getExpenseFields(groupUiModel)
            PaymentType.SETTLEMENT -> getSettlementFields(groupUiModel)
        }

    private fun getExpenseFields(groupUiModel: GroupUiModel) = ImmutableSet.of(
        PaymentContract.Field.DescriptionField(
            value = "",
            visible = true,
        ),
        PaymentContract.Field.ValueField(
            value = "",
            visible = true,
        ),
        PaymentContract.Field.DateField(
            value = Calendar.getInstance().timeInMillis,
            visible = true,
        ),
        PaymentContract.Field.PaidByField(
            value = groupUiModel.members.firstOrNull() ?: GroupMemberUiModel(),
            options = groupUiModel.membersToSelect,
            visible = true,
        ),
        PaymentContract.Field.PaidToField(
            value = groupUiModel.members,
            options = groupUiModel.members,
            visible = true,
            isMultiSelect = true,
        )
    )

    private fun getSettlementFields(groupUiModel: GroupUiModel) = ImmutableSet.of(
        PaymentContract.Field.DescriptionField(
            value = stringProvider.getString(R.string.label_settlement),
            visible = false,
        ),
        PaymentContract.Field.ValueField(
            value = "",
            visible = true,
        ),
        PaymentContract.Field.DateField(
            value = Calendar.getInstance().timeInMillis,
            visible = false,
        ),
        PaymentContract.Field.PaidByField(
            value = groupUiModel.members.firstOrNull() ?: GroupMemberUiModel(),
            options = groupUiModel.membersToSelect,
            visible = true,
        ),
        PaymentContract.Field.PaidToField(
            value = ImmutableSet.copyOf(groupUiModel.members.drop(1).take(1)),
            options = ImmutableSet.copyOf(groupUiModel.members.drop(1)),
            visible = true,
            isMultiSelect = false,
        )
    )

    fun calculateSharedValue(value: String, paidToCount: Int): String {
        val valueInBigDecimal = value.toBigDecimalOrNull() ?: BigDecimal.ZERO
        val paidToCountInBigDecimal = BigDecimal.valueOf(paidToCount.toLong().takeIf { it > 0 } ?: 1)
        return (valueInBigDecimal / paidToCountInBigDecimal).toCurrency()
    }

    fun mapErrorHandler(inputFields: ImmutableSet<PaymentContract.Field>, error: PaymentUiError) =
        inputFields.map { field ->
            if (field::class == error.errorHandler) {
                when (field) {
                    is PaymentContract.Field.DescriptionField -> field.copy(error = error)
                    is PaymentContract.Field.ValueField -> field.copy(error = error)
                    is PaymentContract.Field.DateField -> field.copy(error = error)
                    is PaymentContract.Field.PaidByField -> field.copy(error = error)
                    is PaymentContract.Field.PaidToField -> field.copy(error = error)
                }
            } else {
                field
            }
        }

}