package br.com.jwar.sharedbill.presentation.ui.screens.group_create

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import br.com.jwar.sharedbill.presentation.navigation.AppScreen.GroupDetails
import br.com.jwar.sharedbill.presentation.ui.screens.group_create.GroupEditContract.Effect.OpenGroupSaved
import br.com.jwar.sharedbill.presentation.ui.screens.group_create.GroupEditContract.Effect.ShowError
import br.com.jwar.sharedbill.presentation.ui.screens.group_create.GroupEditContract.Event.OnSaveClick
import br.com.jwar.sharedbill.presentation.ui.screens.group_create.components.GroupEditContent

@Composable
fun GroupEditScreen(
    navController: NavController,
    viewModel: GroupEditViewModel = hiltViewModel()
) {
    val state = viewModel.uiState.collectAsState().value
    val snackHostState = remember { SnackbarHostState() }

    GroupEditContent(
        state = state,
        snackHostState = snackHostState,
        onCreateGroupClick = {
            viewModel.emitEvent { OnSaveClick(name = it) }
        }
    )

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when(effect) {
                is OpenGroupSaved -> {
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