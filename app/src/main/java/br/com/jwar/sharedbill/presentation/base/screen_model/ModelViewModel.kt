package br.com.jwar.sharedbill.presentation.base.screen_model

import androidx.lifecycle.viewModelScope
import br.com.jwar.sharedbill.core.common.BaseViewModel
import br.com.jwar.sharedbill.presentation.base.screen_model.ModelContract.Effect
import br.com.jwar.sharedbill.presentation.base.screen_model.ModelContract.Event
import br.com.jwar.sharedbill.presentation.base.screen_model.ModelContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class ModelViewModel @Inject constructor(
): br.com.jwar.sharedbill.core.common.BaseViewModel<Event, State, Effect>() {

    override fun getInitialState(): State = State.Loading

    override fun handleEvent(event: Event) {
        when(event) {
            is Event.OnLoad -> onLoad()
        }
    }

    private fun onLoad() = viewModelScope.launch {}
}