package br.com.jwar.sharedbill.groups.presentation.ui.group_details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.jwar.sharedbill.groups.domain.model.PaymentType
import br.com.jwar.sharedbill.groups.presentation.ui.group_details.GroupDetailsContract.Effect
import br.com.jwar.sharedbill.groups.presentation.ui.group_details.GroupDetailsContract.Event
import br.com.jwar.sharedbill.core.utility.extensions.shareText

@Composable
fun GroupDetailsRoute(
    groupId: String,
    viewModel: GroupDetailsViewModel = hiltViewModel(),
    onNavigateToGroupEdit: (String) -> Unit = {},
    onNavigateToNewPayment: (String, PaymentType) -> Unit = { _, _ -> },
    onNavigateBack: () -> Unit
) {
    val state = viewModel.uiState.collectAsState().value
    val context = LocalContext.current

    GroupDetailsScreen(
        state = state,
        onNavigateBack = onNavigateBack,
        onNewPaymentClick = { type -> viewModel.emitEvent { Event.OnNewPayment(type) } },
        onEditClick = { viewModel.emitEvent { Event.OnEditGroup } },
        onShareBalance = { balance -> viewModel.emitEvent { Event.OnShareBalance(balance) }},
        onDeletePayment = { paymentId, groupId -> viewModel.emitEvent { Event.OnDeletePayment(paymentId, groupId) }},
    )

    LaunchedEffect(Unit) {
        viewModel.emitEvent { Event.OnInit(groupId) }
        viewModel.uiEffect.collect { effect ->
            when(effect) {
                is Effect.NavigateToGroupEdit -> {
                    onNavigateToGroupEdit(groupId)
                }
                is Effect.NavigateToNewPayment -> {
                    onNavigateToNewPayment(groupId, effect.paymentType)
                }
                is Effect.ShareBalance -> {
                    context.shareText(effect.balance)
                }
            }
        }
    }
}