package br.com.jwar.sharedbill.presentation.ui.screens.payment

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import br.com.jwar.sharedbill.presentation.ui.screens.payment.PaymentContract.Effect
import br.com.jwar.sharedbill.presentation.ui.screens.payment.PaymentContract.Event
import br.com.jwar.sharedbill.presentation.ui.screens.payment.components.PaymentContent

@Composable
fun PaymentScreen(
    navController: NavController,
    viewModel: PaymentViewModel = hiltViewModel(),
    groupId: String
) {
    val state = viewModel.uiState.collectAsState().value

    PaymentContent(
        state = state,
        onSendPaymentClick = { payment, group ->
            viewModel.emitEvent { Event.SendPayment(payment, group) }
        }
    )

    LaunchedEffect(Unit) {
        viewModel.emitEvent { Event.OnRequestGroup(groupId) }
        viewModel.uiEffect.collect { effect ->
            when(effect) {
                is Effect.Finish -> {
                    navController.popBackStack()
                }
            }
        }
    }
}