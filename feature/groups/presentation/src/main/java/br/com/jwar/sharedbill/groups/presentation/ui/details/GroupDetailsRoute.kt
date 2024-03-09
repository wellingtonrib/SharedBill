package br.com.jwar.sharedbill.groups.presentation.ui.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.jwar.sharedbill.core.utility.extensions.shareText
import br.com.jwar.sharedbill.groups.domain.model.PaymentType
import br.com.jwar.sharedbill.groups.presentation.ui.details.GroupDetailsContract.Effect
import br.com.jwar.sharedbill.groups.presentation.ui.details.GroupDetailsContract.Event

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
    var isInitialized by rememberSaveable { mutableStateOf(false) }

    GroupDetailsScreen(
        state = state,
        onNavigateBack = onNavigateBack,
        onNewPaymentClick = { type -> viewModel.emitEvent { Event.OnNewPayment(type) } },
        onEditClick = { viewModel.emitEvent { Event.OnEditGroup } },
        onShareBalance = { balance -> viewModel.emitEvent { Event.OnShareBalance(balance) } },
        onDeletePayment = { paymentId, _ ->
            viewModel.emitEvent { Event.OnDeletePayment(paymentId, groupId) }
        },
    )

    LaunchedEffect(Unit) {
        if (!isInitialized) {
            viewModel.emitEvent { Event.OnInit(groupId) }
            isInitialized = true
        }
        viewModel.uiEffect.collect { effect ->
            when (effect) {
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
