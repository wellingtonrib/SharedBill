package br.com.jwar.sharedbill.presentation.ui.screens.group_create

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import br.com.jwar.sharedbill.presentation.navigation.AppScreen.GroupDetails
import br.com.jwar.sharedbill.presentation.ui.screens.group_create.GroupCreateContract.Effect.OpenGroupCreated
import br.com.jwar.sharedbill.presentation.ui.screens.group_create.GroupCreateContract.Effect.ShowError
import br.com.jwar.sharedbill.presentation.ui.screens.group_create.GroupCreateContract.Event.OnCreateGroup
import br.com.jwar.sharedbill.presentation.ui.screens.group_create.components.GroupCreateContent

@ExperimentalMaterial3Api
@Composable
fun GroupCreateScreen(
    navController: NavController,
    viewModel: GroupCreateViewModel = hiltViewModel()
) {
    val state = viewModel.uiState.collectAsState().value
    val snackHostState = remember { SnackbarHostState() }

    GroupCreateContent(
        state = state,
        snackHostState = snackHostState,
        onCreateGroupClick = {
            viewModel.emitEvent { OnCreateGroup(name = it) }
        }
    )

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when(effect) {
                is OpenGroupCreated -> {
                    with(navController) {
                        popBackStack()
                        navigate(GroupDetails.createRoute(effect.group.id))
                    }
                }
                is ShowError -> snackHostState.showSnackbar(effect.message)
            }
        }
    }
}