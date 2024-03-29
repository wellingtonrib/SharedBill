package br.com.jwar.sharedbill.groups.presentation.ui.payment

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.jwar.sharedbill.groups.domain.model.PaymentType
import br.com.jwar.sharedbill.groups.presentation.ui.payment.GroupPaymentContract.Effect
import br.com.jwar.sharedbill.groups.presentation.ui.payment.GroupPaymentContract.Event

@Composable
fun PaymentRoute(
    groupId: String,
    paymentType: PaymentType,
    viewModel: GroupPaymentViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {}
) {
    val state = viewModel.uiState.collectAsState().value

    PaymentScreen(
        state = state,
        onDescriptionChange = { viewModel.emitEvent { Event.OnDescriptionChange(it) } },
        onValueChange = { viewModel.emitEvent { Event.OnValueChange(it) } },
        onDateChange = { viewModel.emitEvent { Event.OnDateChange(it) } },
        onPaidByChange = { viewModel.emitEvent { Event.OnPaidByChange(it) } },
        onPaidToChange = { viewModel.emitEvent { Event.OnPaidToChange(it) } },
        onSaveClick = { viewModel.emitEvent { Event.OnSavePayment(groupId, paymentType) } },
        onNavigateBack = onNavigateBack,
    )

    LaunchedEffect(Unit) {
        viewModel.emitEvent { Event.OnInit(groupId, paymentType) }
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is Effect.Finish -> onNavigateBack()
            }
        }
    }
}
