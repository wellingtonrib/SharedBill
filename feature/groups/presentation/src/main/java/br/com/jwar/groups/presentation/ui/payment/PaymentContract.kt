package br.com.jwar.groups.presentation.ui.payment

import br.com.jwar.groups.presentation.models.GroupMemberUiModel
import br.com.jwar.groups.presentation.models.PaymentUiError
import br.com.jwar.groups.presentation.models.PaymentUiModel
import br.com.jwar.sharedbill.core.common.UiEffect
import br.com.jwar.sharedbill.core.common.UiEvent
import br.com.jwar.sharedbill.core.common.UiState

class PaymentContract {

    sealed class Event: UiEvent {
        data class OnSavePayment(val payment: PaymentUiModel) : Event()
    }

    data class State(
        val isLoading: Boolean = false,
        val title: String = "",
        val inputFields: List<Field> = emptyList(),
        val genericError: PaymentUiError? = null,
        val paymentUiModel: PaymentUiModel = PaymentUiModel()
    ): UiState

    sealed class Field {
        data class DescriptionField(
            override val requestFocus: Boolean = false,
            override val error: PaymentUiError? = null,
        ): Field(), Focusable, ErrorHandler
        data class ValueField(
            override val requestFocus: Boolean = false,
            override val error: PaymentUiError? = null,
        ): Field(), Focusable, ErrorHandler
        data class DateField(
            override val error: PaymentUiError? = null,
        ): Field(), ErrorHandler
        data class PaidByField(
            val options: Map<GroupMemberUiModel, Boolean>,
            override val error: PaymentUiError? = null,
        ): Field(), ErrorHandler
        data class PaidToField(
            val options: List<GroupMemberUiModel>,
            val isMultiSelect: Boolean = true,
            override val error: PaymentUiError? = null,
        ): Field(), ErrorHandler

        val hasError: Boolean
            get() = this is ErrorHandler && error != null
    }

    interface Focusable {
        val requestFocus: Boolean
    }

    interface ErrorHandler {
        val error: PaymentUiError?
    }

    sealed class Effect: UiEffect {
        object Finish: Effect()
    }
}