package br.com.jwar.sharedbill

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.jwar.sharedbill.account.domain.model.User
import br.com.jwar.sharedbill.account.domain.usecases.GetCurrentUserUseCase
import br.com.jwar.sharedbill.account.presentation.navigation.AUTH_ROUTE
import br.com.jwar.sharedbill.groups.presentation.ui.group_list.GROUP_LIST_ROUTE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    init {
        viewModelScope.launch {
            val currentUser = getCurrentUserUseCase().getOrNull()
            _uiState.value = UiState.Loaded(currentUser)
        }
    }

    sealed class UiState {
        object Loading : UiState()
        data class Loaded(val currentUser: User?) : UiState()

        val startDestination
            get() = if (this is Loaded && this.currentUser != null) GROUP_LIST_ROUTE else AUTH_ROUTE
    }
}