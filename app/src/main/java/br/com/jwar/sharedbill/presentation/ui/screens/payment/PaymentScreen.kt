package br.com.jwar.sharedbill.presentation.ui.screens.payment

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.jwar.sharedbill.R
import br.com.jwar.sharedbill.presentation.navigation.AppTopBar
import br.com.jwar.sharedbill.presentation.navigation.CloseNavigationIcon
import br.com.jwar.sharedbill.presentation.ui.screens.payment.PaymentContract.Effect
import br.com.jwar.sharedbill.presentation.ui.screens.payment.PaymentContract.Event
import br.com.jwar.sharedbill.presentation.ui.screens.payment.components.PaymentContent

@Composable
fun PaymentScreen(
    navigateBack: () -> Unit = {},
    snackbarHostState: SnackbarHostState,
    viewModel: PaymentViewModel = hiltViewModel(),
    groupId: String
) {
    val state = viewModel.uiState.collectAsState().value
    val context = LocalContext.current

    Column {
        AppTopBar(
            navigationBack = navigateBack,
            navigationIcon = { CloseNavigationIcon(navigateBack) },
            title = stringResource(id = R.string.label_payment_new),
            actions = {
                IconButton(onClick = {
                    viewModel.emitEvent { Event.OnCreatePayment }
                }) {
                    Icon(Icons.Filled.Done, stringResource(id = R.string.description_done))
                }
            }
        )
        PaymentContent(
            state = state,
            onPaymentParamsChange = { viewModel.emitEvent { Event.OnPaymentParamsChange(it) }},
        )
    }

    LaunchedEffect(Unit) {
        viewModel.emitEvent { Event.OnInit(groupId) }
        viewModel.uiEffect.collect { effect ->
            when(effect) {
                is Effect.Finish -> {
                    navigateBack()
                }
                is Effect.ShowError -> {
                    snackbarHostState.showSnackbar(effect.message.asString(context))
                }
            }
        }
    }
}