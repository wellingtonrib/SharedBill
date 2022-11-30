package br.com.jwar.sharedbill.presentation.ui.screens.payment

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.jwar.sharedbill.presentation.ui.screens.payment.PaymentContract.Effect
import br.com.jwar.sharedbill.presentation.ui.screens.payment.PaymentContract.Event
import br.com.jwar.sharedbill.presentation.ui.screens.payment.components.PaymentScreen

@Composable
fun PaymentRoute(
    groupId: String,
    viewModel: PaymentViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
    onNavigateBack: () -> Unit = {}
) {
    val state = viewModel.uiState.collectAsState().value
    val context = LocalContext.current

    PaymentScreen(
        state = state,
        onPaymentParamsChange = { viewModel.emitEvent { Event.OnParamsChange(it) }},
        onSaveClick = { viewModel.emitEvent { Event.OnCreatePayment } },
        onNavigateBack = onNavigateBack,
    )


    LaunchedEffect(Unit) {
        viewModel.emitEvent { Event.OnInit(groupId) }
        viewModel.uiEffect.collect { effect ->
            when(effect) {
                is Effect.Finish -> onNavigateBack()
                is Effect.ShowError -> {
                    snackbarHostState.showSnackbar(effect.message.asString(context))
                }
            }
        }
    }
}