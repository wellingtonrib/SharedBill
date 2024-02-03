package br.com.jwar.sharedbill

import androidx.lifecycle.viewModelScope
import br.com.jwar.sharedbill.MainContract.Effect
import br.com.jwar.sharedbill.MainContract.Event
import br.com.jwar.sharedbill.MainContract.State
import br.com.jwar.sharedbill.account.domain.usecases.GetCurrentUserUseCase
import br.com.jwar.sharedbill.core.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase
): BaseViewModel<Event, State, Effect>() {
    override fun getInitialState() = State.Initializing
    override fun handleEvent(event: Event) {
        when (event) {
            is Event.OnInit -> onInit()
        }
    }

    private fun onInit() = viewModelScope.launch {
        val currentUser = getCurrentUserUseCase().getOrNull()
        setState { State.Initialized(currentUser) }
        if (currentUser == null) {
            sendEffect { Effect.NavigateToAuth }
        }
    }

}