package br.com.jwar.sharedbill.presentation.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<Event: UiEvent, State: UiState, Effect: UiEffect> : ViewModel() {

    private val _initialState : State by lazy { getInitialState() }
    abstract fun getInitialState() : State

    private val _uiState = MutableStateFlow<State>(_initialState)
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<Event>()
    val uiEvent = _uiEvent.asSharedFlow()

    private val _uiEffect = Channel<Effect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    abstract fun handleEvent(event: Event)

    init {
        subscribeEvents()
    }

    private fun subscribeEvents() = viewModelScope.launch {
        _uiEvent.collect {
            handleEvent(it)
        }
    }

    fun emitEvent(event: () -> Event) {
        viewModelScope.launch {
            _uiEvent.emit(event())
        }
    }

    protected fun setState(update: (State) -> State) {
        _uiState.update(update)
    }

    protected fun sendEffect(effect: () -> Effect) {
        viewModelScope.launch {
            _uiEffect.send(effect())
        }
    }
}