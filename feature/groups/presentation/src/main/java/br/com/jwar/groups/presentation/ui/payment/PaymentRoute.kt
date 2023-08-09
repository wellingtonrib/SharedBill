package br.com.jwar.groups.presentation.ui.payment

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.jwar.sharedbill.groups.domain.model.PaymentType
import br.com.jwar.groups.presentation.ui.payment.PaymentContract.Effect
import br.com.jwar.groups.presentation.ui.payment.PaymentContract.Event

@Composable
fun PaymentRoute(
    groupId: String,
    paymentType: PaymentType,
    viewModel: PaymentViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {}
) {
    val state = viewModel.uiState.collectAsState().value

    PaymentScreen(
        state = state,
        onPaymentParamsChange = { viewModel.emitEvent { Event.OnParamsChange(it) }},
        onSaveClick = { viewModel.emitEvent { Event.OnSavePayment } },
        onNavigateBack = onNavigateBack,
    )

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when(effect) {
                is Effect.Finish -> onNavigateBack()
            }
        }
    }
}