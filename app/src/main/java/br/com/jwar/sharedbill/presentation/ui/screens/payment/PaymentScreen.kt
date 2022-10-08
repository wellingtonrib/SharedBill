package br.com.jwar.sharedbill.presentation.ui.screens.payment

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
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
    val snackHostState = remember { SnackbarHostState() }

    PaymentContent(
        state = state,
        snackHostState = snackHostState,
        onSendPaymentClick = { params ->
            viewModel.emitEvent { Event.SendPayment(params) }
        }
    )

    LaunchedEffect(Unit) {
        viewModel.emitEvent { Event.OnRequestGroup(groupId) }
        viewModel.uiEffect.collect { effect ->
            when(effect) {
                is Effect.Finish -> {
                    navController.popBackStack()
                }
                is Effect.ShowError -> {
                    snackHostState.showSnackbar(effect.message)
                }
            }
        }
    }
}