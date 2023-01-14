package br.com.jwar.sharedbill.presentation.ui.screens.group_list

import br.com.jwar.sharedbill.presentation.base.UiEffect
import br.com.jwar.sharedbill.presentation.base.UiEvent
import br.com.jwar.sharedbill.presentation.base.UiState
import br.com.jwar.sharedbill.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.presentation.ui.util.UiText

class GroupListContract {

    sealed class Event: UiEvent {
        object OnTryAgain : Event()
        class OnGroupCreate(val title: String): Event()
        class OnGroupJoin(val inviteCode: String): Event()
        class OnGroupSelect(val groupId: String): Event()
        class OnGroupDelete(val groupId: String) : Event()
        class OnGroupLeave(val groupId: String) : Event()
    }

    sealed class State: UiState {
        object Loading: State()
        data class Loaded(val uiModel: List<GroupUiModel>): State()
        class Error(val message: String?): State()
    }

    sealed class Effect: UiEffect {
        class OpenGroupDetails(val groupId: String): Effect()
        class Error(val message: UiText): Effect()
        object GoToAuth : Effect()
    }

}