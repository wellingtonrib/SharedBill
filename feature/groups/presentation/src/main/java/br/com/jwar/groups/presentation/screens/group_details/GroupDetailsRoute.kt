package br.com.jwar.groups.presentation.screens.group_details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.jwar.groups.presentation.screens.group_details.GroupDetailsContract.Effect
import br.com.jwar.groups.presentation.screens.group_details.GroupDetailsContract.Event

@Composable
fun GroupDetailsRoute(
    groupId: String,
    viewModel: GroupDetailsViewModel = hiltViewModel(),
    onNavigateToGroupEdit: (String) -> Unit = {},
    onNavigateToNewPayment: (String) -> Unit = {},
    onNavigateBack: () -> Unit
) {
    val state = viewModel.uiState.collectAsState().value

    GroupDetailsScreen(
        state = state,
        onNavigateBack = onNavigateBack,
        onNewPaymentClick = { viewModel.emitEvent { Event.OnNewPayment(groupId) } },
        onRefresh = { viewModel.emitEvent { Event.OnRefreshGroup } },
        onEditClick = { viewModel.emitEvent { Event.OnEditGroup } }
    )

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when(effect) {
                is Effect.NavigateToGroupEdit -> {
                    onNavigateToGroupEdit(groupId)
                }
                is Effect.NavigateToNewPayment -> {
                    onNavigateToNewPayment(groupId)
                }
            }
        }
    }
}
