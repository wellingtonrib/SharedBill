package br.com.jwar.sharedbill.presentation.ui.screens.group_details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.jwar.sharedbill.presentation.ui.screens.group_details.GroupDetailsContract.Effect
import br.com.jwar.sharedbill.presentation.ui.screens.group_details.GroupDetailsContract.Event
import br.com.jwar.sharedbill.presentation.ui.screens.group_details.components.GroupDetailsScreen

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
        onRefresh = { viewModel.emitEvent { Event.OnRefresh } },
        onEditClick = { viewModel.emitEvent { Event.OnManage } }
    )

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when(effect) {
                is Effect.OpenGroupEdit -> {
                    onNavigateToGroupEdit(groupId)
                }
                is Effect.OpenNewPayment -> {
                    onNavigateToNewPayment(groupId)
                }
            }
        }
    }
}
