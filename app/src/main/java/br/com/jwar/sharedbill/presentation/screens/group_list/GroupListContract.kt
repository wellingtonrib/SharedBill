package br.com.jwar.sharedbill.presentation.screens.group_list

import br.com.jwar.sharedbill.core.common.UiEffect
import br.com.jwar.sharedbill.core.common.UiEvent
import br.com.jwar.sharedbill.core.common.UiState
import br.com.jwar.sharedbill.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.core.designsystem.util.UiText

class GroupListContract {

    sealed class Event: br.com.jwar.sharedbill.core.common.UiEvent {
        object OnTryAgain : Event()
        class OnGroupCreate(val title: String): Event()
        class OnGroupJoin(val inviteCode: String): Event()
        class OnGroupSelect(val groupId: String): Event()
        class OnGroupDelete(val groupId: String) : Event()
        class OnGroupLeave(val groupId: String) : Event()
    }

    sealed class State: br.com.jwar.sharedbill.core.common.UiState {
        object Loading: State()
        data class Loaded(val uiModel: List<GroupUiModel>): State()
        class Error(val message: String?): State()
    }

    sealed class Effect: br.com.jwar.sharedbill.core.common.UiEffect {
        class OpenGroupDetails(val groupId: String): Effect()
        class Error(val message: UiText): Effect()
        object GoToAuth : Effect()
    }

}