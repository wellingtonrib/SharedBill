package br.com.jwar.groups.presentation.ui.payment.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import br.com.jwar.groups.presentation.models.PaymentUiError
import br.com.jwar.groups.presentation.models.PaymentUiModel
import br.com.jwar.groups.presentation.ui.payment.PaymentContract
import br.com.jwar.sharedbill.core.designsystem.components.AppTopBar
import br.com.jwar.sharedbill.core.designsystem.components.CloseNavigationIcon
import br.com.jwar.sharedbill.core.designsystem.components.Field
import br.com.jwar.sharedbill.core.designsystem.theme.AppTheme
import br.com.jwar.sharedbill.core.designsystem.theme.paddingMedium
import br.com.jwar.sharedbill.groups.domain.model.PaymentType
import br.com.jwar.sharedbill.groups.presentation.R
import java.util.Date
import br.com.jwar.sharedbill.core.designsystem.R as DSR

@Composable
fun SettlementPaymentContent(
    modifier: Modifier = Modifier,
    state: PaymentContract.State.Loaded,
    onSaveClick: (payment: PaymentUiModel) -> Unit,
    onNavigateBack: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val focusManager = remember { FocusRequester() }

    val description = stringResource(R.string.label_settlement)
    val dateTime = remember { Date().time }
    var value by remember { mutableStateOf("") }
    var paidBy by remember { mutableStateOf(state.paidByDefault) }
    var paidTo by remember { mutableStateOf(listOf(state.paidToDefault)) }

    var valueError by remember { mutableStateOf<PaymentUiError.InvalidValueError?>(null) }

    LaunchedEffect(state.error) {
        when(state.error) {
            is PaymentUiError.InvalidValueError -> valueError = state.error
            else -> state.error?.message?.asString(context)?.let { snackbarHostState.showSnackbar(it) }
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            AppTopBar(
                navigationBack = onNavigateBack,
                navigationIcon = { CloseNavigationIcon(onNavigateBack) },
                title = stringResource(R.string.label_payment_new_settlement),
                actions = {
                    IconButton(onClick = {
                        onSaveClick(
                            PaymentUiModel(
                                description = description,
                                value = value,
                                paidBy = paidBy,
                                paidTo = paidTo,
                                createdAt = Date(dateTime),
                                paymentType = PaymentType.SETTLEMENT
                            )
                        )
                        keyboardController?.hide()
                    }) {
                        Icon(Icons.Filled.Done, stringResource(id = DSR.string.description_done))
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = modifier.padding(innerPadding)) {
            Column(
                modifier = modifier.paddingMedium(),
                verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.space_4)
            ) {
                Field {
                    LaunchedEffect(Unit) {
                        focusManager.requestFocus()
                    }
                    PaymentValueField(
                        focusManager = focusManager,
                        value = value,
                        error = valueError,
                    ) { newValue -> value = newValue; valueError = null }
                }
                Field {
                    PaymentPaidByField(
                        paidBy = paidBy,
                        paidByOptions = state.paidByOptions,
                    ) { newPaidBy -> paidBy = newPaidBy }
                }
                Field {
                    PaymentPaidToField(
                        paidTo = paidTo,
                        paidToOptions = state.paidToOptions,
                        isExpense = false,
                    ) { newPaidTo -> paidTo = newPaidTo }
                }
            }
        }
    }
}