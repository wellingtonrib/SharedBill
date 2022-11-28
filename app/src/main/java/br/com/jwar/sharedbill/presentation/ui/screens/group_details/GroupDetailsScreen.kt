package br.com.jwar.sharedbill.presentation.ui.screens.group_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.jwar.sharedbill.R
import br.com.jwar.sharedbill.presentation.navigation.AppTopBar
import br.com.jwar.sharedbill.presentation.ui.screens.group_details.GroupDetailsContract.*
import br.com.jwar.sharedbill.presentation.ui.screens.group_details.components.GroupDetailsContent
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun GroupDetailsScreen(
    navigateToGroupEdit: (String) -> Unit = {},
    navigateToNewPayment: (String) -> Unit = {},
    navigateBack: () -> Unit,
    groupId: String,
    viewModel: GroupDetailsViewModel = hiltViewModel()
) {
    val state = viewModel.uiState.collectAsState().value

    Column(modifier = Modifier.fillMaxWidth()) {
        AppTopBar(
            navigationBack = navigateBack,
            title = stringResource(id = R.string.label_group_details),
            actions = {
                IconButton(onClick = { viewModel.emitEvent { Event.OnManageClick } }) {
                    Icon(Icons.Filled.Edit, stringResource(id = R.string.label_group_manage))
                }
            }
        )
        SwipeRefresh(
            state = rememberSwipeRefreshState(state is State.Loading),
            onRefresh = {
                viewModel.emitEvent { Event.OnInit(groupId) }
            }
        ) {
            GroupDetailsContent(
                state = state,
                onNewPaymentClick = { groupId ->
                    viewModel.emitEvent { Event.OnNewPaymentClick(groupId) }
                },
            )
        }
    }

    LaunchedEffect(Unit) {
        viewModel.emitEvent { Event.OnInit(groupId) }
        viewModel.uiEffect.collect { effect ->
            when(effect) {
                is Effect.OpenGroupMembers -> {
                    navigateToGroupEdit(groupId)
                }
                is Effect.OpenNewPayment -> {
                    navigateToNewPayment(groupId)
                }
            }
        }
    }
}
