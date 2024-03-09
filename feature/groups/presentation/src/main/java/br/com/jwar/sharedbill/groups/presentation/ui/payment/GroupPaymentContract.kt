package br.com.jwar.sharedbill.groups.presentation.ui.payment

import androidx.annotation.StringRes
import br.com.jwar.sharedbill.core.common.UiEffect
import br.com.jwar.sharedbill.core.common.UiEvent
import br.com.jwar.sharedbill.core.common.UiState
import br.com.jwar.sharedbill.groups.domain.model.PaymentType
import br.com.jwar.sharedbill.groups.presentation.R
import br.com.jwar.sharedbill.groups.presentation.models.GroupMemberUiModel
import br.com.jwar.sharedbill.groups.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.groups.presentation.models.PaymentUiError
import com.google.common.collect.ImmutableMap
import com.google.common.collect.ImmutableSet
import java.util.Date

class GroupPaymentContract {

    sealed class Event : UiEvent {
        data class OnInit(val groupId: String, val paymentType: PaymentType) : Event()
        data class OnSavePayment(val groupId: String, val paymentType: PaymentType) : Event()
        data class OnDescriptionChange(val description: String) : Event()
        data class OnValueChange(val value: String) : Event()
        data class OnDateChange(val dateTime: Long) : Event()
        data class OnPaidByChange(val paidBy: GroupMemberUiModel) : Event()
        data class OnPaidToChange(val paidTo: ImmutableMap<GroupMemberUiModel, Int>) : Event()
    }

    data class State(
        val isLoading: Boolean = false,
        val inputFields: ImmutableSet<Field> = ImmutableSet.of(),
        val groupUiModel: GroupUiModel = GroupUiModel(),
        val genericError: PaymentUiError? = null,
        val paymentType: PaymentType = PaymentType.EXPENSE,
    ) : UiState {

        @get:StringRes
        val titleRes: Int
            get() = when (paymentType) {
                PaymentType.EXPENSE -> R.string.label_payment_new_expense
                PaymentType.SETTLEMENT -> R.string.label_payment_new_settlement
                else -> R.string.label_payment_refund
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
        ) : Field(value)
        data class ValueField(
            override val value: String = "",
            override val visible: Boolean = true,
            override val error: PaymentUiError? = null,
        ) : Field(value)
        data class DateField(
            override val value: Long = Date().time,
            override val visible: Boolean = true,
            override val error: PaymentUiError? = null,
        ) : Field(value)
        data class PaidByField(
            override val value: GroupMemberUiModel = GroupMemberUiModel(),
            override val visible: Boolean = true,
            override val error: PaymentUiError? = null,
            val options: ImmutableMap<GroupMemberUiModel, Boolean>,
        ) : Field(value)
        data class PaidToField(
            override val value: ImmutableMap<GroupMemberUiModel, Int> = ImmutableMap.of(),
            override val visible: Boolean = true,
            override val error: PaymentUiError? = null,
            val options: ImmutableSet<GroupMemberUiModel>,
            val isMultiSelect: Boolean = true,
            val sharedValue: ImmutableMap<GroupMemberUiModel, String> = ImmutableMap.of(),
        ) : Field(value)

        val hasError: Boolean
            get() = error != null
    }

    sealed class Effect : UiEffect {
        object Finish : Effect()
    }
}

inline fun <reified T : GroupPaymentContract.Field> GroupPaymentContract.State.updateField(
    updater: (T) -> GroupPaymentContract.Field
): GroupPaymentContract.State {
    val updatedFields = this.inputFields.map { field ->
        if (field is T) updater(field) else field
    }
    return this.copy(inputFields = ImmutableSet.copyOf(updatedFields))
}

inline fun GroupPaymentContract.State.updateFields(
    updater: (GroupPaymentContract.Field) -> GroupPaymentContract.Field
): GroupPaymentContract.State {
    val updatedFields = this.inputFields.map { field -> updater(field) }
    return this.copy(inputFields = ImmutableSet.copyOf(updatedFields))
}

@Suppress("UNCHECKED_CAST")
@SuppressWarnings("unchecked")
inline fun <reified T : GroupPaymentContract.Field, V> GroupPaymentContract.State.getFieldValue() =
    this.inputFields.filterIsInstance<T>().firstOrNull()?.value as? V
