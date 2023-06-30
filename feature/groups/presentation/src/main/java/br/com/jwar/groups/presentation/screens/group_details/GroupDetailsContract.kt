package br.com.jwar.groups.presentation.screens.group_details

import br.com.jwar.groups.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.core.common.UiEffect
import br.com.jwar.sharedbill.core.common.UiEvent
import br.com.jwar.sharedbill.core.common.UiState

class GroupDetailsContract {

    sealed class Event: UiEvent {
        object OnRefreshGroup : Event()
        object OnEditGroup : Event()
        class OnNewPayment(val groupId: String) : Event()
    }

    sealed class State: UiState {
        object Loading: State()
        class Loaded(val uiModel: GroupUiModel): State()
        class Error(val message: String): State()
        fun getTitle() = if (this is Loaded) this.uiModel.title else ""
    }

    sealed class Effect: UiEffect {
        object NavigateToGroupEdit: Effect()
        class NavigateToNewPayment(val groupId: String): Effect()
    }

}