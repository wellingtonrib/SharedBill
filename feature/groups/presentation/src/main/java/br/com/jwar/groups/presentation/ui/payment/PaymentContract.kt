package br.com.jwar.groups.presentation.ui.payment

import androidx.annotation.StringRes
import br.com.jwar.groups.presentation.models.GroupMemberUiModel
import br.com.jwar.groups.presentation.models.PaymentUiError
import br.com.jwar.sharedbill.core.common.UiEffect
import br.com.jwar.sharedbill.core.common.UiEvent
import br.com.jwar.sharedbill.core.common.UiState
import br.com.jwar.sharedbill.groups.domain.model.PaymentType
import br.com.jwar.sharedbill.groups.presentation.R
import com.google.common.collect.ImmutableMap
import com.google.common.collect.ImmutableSet
import java.util.Date

class PaymentContract {

    sealed class Event: UiEvent {
        object OnSavePayment : Event()
        data class OnDescriptionChange(val description: String) : Event()
        data class OnValueChange(val value: String) : Event()
        data class OnDateChange(val dateTime: Long) : Event()
        data class OnPaidByChange(val paidBy: GroupMemberUiModel) : Event()
        data class OnPaidToChange(val paidTo: ImmutableSet<GroupMemberUiModel>) : Event()
    }

    data class State(
        val isLoading: Boolean = false,
        val inputFields: ImmutableSet<Field> = ImmutableSet.of(),
        val genericError: PaymentUiError? = null,
        val paymentType: PaymentType = PaymentType.EXPENSE,
    ): UiState {

        @get:StringRes
        val titleRes: Int
            get() = when (paymentType) {
                PaymentType.EXPENSE -> R.string.label_payment_new_expense
                PaymentType.SETTLEMENT -> R.string.label_payment_new_settlement
            }

        val visibleFields: ImmutableSet<Field>
            get() = ImmutableSet.copyOf(inputFields.filter { it.visible })
    }

    sealed class Field(
        open val value: Any,
        open val visible: Boolean = true,
        open val error: PaymentUiError? = null,
    ) {
        data class DescriptionField(
            override val value: String = "",
            override val visible: Boolean = true,
            override val error: PaymentUiError? = null,
        ): Field(value)
        data class ValueField(
            override val value: String = "",
            override val visible: Boolean = true,
            override val error: PaymentUiError? = null,
        ): Field(value)
        data class DateField(
            override val value: Long = Date().time,
            override val visible: Boolean = true,
            override val error: PaymentUiError? = null,
        ): Field(value)
        data class PaidByField(
            override val value: GroupMemberUiModel = GroupMemberUiModel(),
            override val visible: Boolean = true,
            override val error: PaymentUiError? = null,
            val options: ImmutableMap<GroupMemberUiModel, Boolean>,
        ): Field(value)
        data class PaidToField(
            override val value: ImmutableSet<GroupMemberUiModel> = ImmutableSet.of(),
            override val visible: Boolean = true,
            override val error: PaymentUiError? = null,
            val options: ImmutableSet<GroupMemberUiModel>,
            val isMultiSelect: Boolean = true,
            val sharedValue: String = "",
        ): Field(value)

        val hasError: Boolean
            get() = error != null
    }

    sealed class Effect: UiEffect {
        object Finish: Effect()
    }
}

inline fun <reified T : PaymentContract.Field> PaymentContract.State.updateField(
    updater: (T) -> PaymentContract.Field
): PaymentContract.State {
    val updatedFields = this.inputFields.map { field ->
        if (field is T) updater(field) else field
    }
    return this.copy(inputFields = ImmutableSet.copyOf(updatedFields))
}

inline fun PaymentContract.State.updateFields(
    updater: (PaymentContract.Field) -> PaymentContract.Field
): PaymentContract.State {
    val updatedFields = this.inputFields.map {
        field -> updater(field)
    }
    return this.copy(inputFields = ImmutableSet.copyOf(updatedFields))
}

@SuppressWarnings("unchecked")
inline fun <reified T: PaymentContract.Field, V> PaymentContract.State.getFieldValue(
) = this.inputFields.filterIsInstance<T>().firstOrNull()?.value as? V