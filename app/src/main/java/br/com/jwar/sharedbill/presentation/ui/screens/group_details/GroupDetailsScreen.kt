package br.com.jwar.sharedbill.presentation.ui.screens.group_details

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import br.com.jwar.sharedbill.presentation.ui.screens.group_details.components.GroupDetailsContent
import br.com.jwar.sharedbill.presentation.ui.screens.group_details.GroupDetailsContract.Event.OnLoadGroup

@ExperimentalMaterial3Api
@Composable
fun GroupDetailsScreen(
    navController: NavController,
    groupId: String,
    viewModel: GroupDetailsViewModel = hiltViewModel()
) {
    val state = viewModel.uiState.collectAsState().value

    GroupDetailsContent(
        state = state,
    )

    LaunchedEffect(Unit) {
        viewModel.emitEvent { OnLoadGroup(groupId) }
    }
}