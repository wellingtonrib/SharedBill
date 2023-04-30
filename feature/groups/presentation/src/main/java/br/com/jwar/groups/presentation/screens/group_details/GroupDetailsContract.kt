package br.com.jwar.groups.presentation.screens.group_details

import br.com.jwar.groups.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.core.common.UiEffect
import br.com.jwar.sharedbill.core.common.UiEvent
import br.com.jwar.sharedbill.core.common.UiState

class GroupDetailsContract {

    sealed class Event: UiEvent {
        object OnRefresh : Event()
        object OnManage : Event()
        class OnNewPayment(val groupId: String) : Event()
    }

    sealed class State: UiState {
        object Loading: State()
        class Loaded(val uiModel: GroupUiModel): State()
        class Error(val message: String): State()
    }

    sealed class Effect: UiEffect {
        object OpenGroupEdit: Effect()
        class OpenNewPayment(val groupId: String): Effect()
    }

}