package br.com.jwar.sharedbill.presentation.base.screen_model

import androidx.lifecycle.viewModelScope
import br.com.jwar.sharedbill.presentation.base.screen_model.ModelContract.Event
import br.com.jwar.sharedbill.presentation.base.screen_model.ModelContract.State
import br.com.jwar.sharedbill.presentation.base.screen_model.ModelContract.Effect
import br.com.jwar.sharedbill.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModelViewModel @Inject constructor(
    //TODO("Not yet implemented")
): BaseViewModel<Event, State, Effect>() {

    override fun getInitialState(): State = State.Loading

    override fun handleEvent(event: Event) {
        when(event) {
            is Event.OnLoad -> onLoad()
        }
    }

    private fun onLoad() = viewModelScope.launch {
        TODO("Not yet implemented")
    }

}