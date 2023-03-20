package br.com.jwar.sharedbill.presentation.screens.group_details

import br.com.jwar.sharedbill.core.common.UiEffect
import br.com.jwar.sharedbill.core.common.UiEvent
import br.com.jwar.sharedbill.core.common.UiState
import br.com.jwar.sharedbill.presentation.models.GroupUiModel

class GroupDetailsContract {

    sealed class Event: br.com.jwar.sharedbill.core.common.UiEvent {
        object OnRefresh : Event()
        object OnManage : Event()
        class OnNewPayment(val groupId: String) : Event()
    }

    sealed class State: br.com.jwar.sharedbill.core.common.UiState {
        object Loading: State()
        class Loaded(val uiModel: GroupUiModel): State()
        class Error(val message: String): State()
    }

    sealed class Effect: br.com.jwar.sharedbill.core.common.UiEffect {
        object OpenGroupEdit: Effect()
        class OpenNewPayment(val groupId: String): Effect()
    }

}