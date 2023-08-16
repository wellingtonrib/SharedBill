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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import br.com.jwar.groups.presentation.models.PaymentUiError
import br.com.jwar.groups.presentation.models.PaymentUiModel
import br.com.jwar.groups.presentation.ui.payment.PaymentContract
import br.com.jwar.sharedbill.core.designsystem.components.AppTopBar
import br.com.jwar.sharedbill.core.designsystem.components.CloseNavigationIcon
import br.com.jwar.sharedbill.core.designsystem.theme.AppTheme
import br.com.jwar.sharedbill.core.designsystem.theme.paddingMedium
import br.com.jwar.sharedbill.core.utility.extensions.orZero
import br.com.jwar.sharedbill.groups.domain.model.PaymentType
import br.com.jwar.sharedbill.groups.presentation.R
import java.math.BigDecimal
import java.util.Calendar
import br.com.jwar.sharedbill.core.designsystem.R as DSR

@Composable
fun ExpensePaymentContent(
    state: PaymentContract.State.Loaded,
    onSaveClick: (payment: PaymentUiModel) -> Unit,
    onNavigateBack: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val calendar = Calendar.getInstance()

    val paidByOptions = remember { state.group.membersForSelect }
    val paidToOptions = remember { state.group.members }

    var description by remember { mutableStateOf("") }
    var value by remember { mutableStateOf("") }
    var date by remember { mutableStateOf(calendar.time) }
    var paidBy by remember { mutableStateOf(state.group.members.first()) }
    var paidTo by remember { mutableStateOf(state.group.members) }

    var descriptionError by remember { mutableStateOf<PaymentUiError.InvalidDescriptionError?>(null) }
    var valueError by remember { mutableStateOf<PaymentUiError.InvalidValueError?>(null) }
    var dateError by remember { mutableStateOf<PaymentUiError.InvalidDateError?>(null) }

    val sharedValue = remember(value, paidTo) {
        derivedStateOf {
            if (paidTo.isEmpty()) BigDecimal.ZERO
            else value.toBigDecimalOrNull()?.div(paidTo.size.toBigDecimal()).orZero()
        }
    }

    LaunchedEffect(state.error) {
        when (state.error) {
            is PaymentUiError.InvalidDescriptionError -> descriptionError = state.error
            is PaymentUiError.InvalidValueError -> valueError = state.error
            is PaymentUiError.InvalidDateError -> dateError = state.error
            else -> state.error?.message?.asString(context)?.let { snackbarHostState.showSnackbar(it) }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            AppTopBar(
                navigationBack = onNavigateBack,
                navigationIcon = { CloseNavigationIcon(onNavigateBack) },
                title = stringResource(R.string.label_payment_new_expense),
                actions = {
                    IconButton(onClick = {
                        onSaveClick(
                            PaymentUiModel(
                                description = description,
                                value = value,
                                paidBy = paidBy,
                                paidTo = paidTo,
                                createdAt = date,
                                paymentType = PaymentType.EXPENSE,
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
        Box(modifier = Modifier.padding(innerPadding)) {
            Column(
                modifier = Modifier.paddingMedium(),
                verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.space_4)
            ) {
                PaymentDescriptionField(
                    description = description,
                    error = descriptionError
                ) { newDescription -> description = newDescription.text; descriptionError = null }
                PaymentValueField(
                    value = value,
                    error = valueError
                ) { newValue -> value = newValue; valueError = null }
                PaymentDateField(
                    date = date,
                    error = dateError
                ) { newDate -> date = newDate }
                PaymentPaidByField(
                    paidBy = paidBy,
                    paidByOptions = paidByOptions,
                ) { newPaidBy -> paidBy = newPaidBy }
                PaymentPaidToField(
                    paidTo = paidTo,
                    sharedValue = sharedValue.value,
                    paidToOptions = paidToOptions,
                    isExpense = true,
                ) { newPaidTo -> paidTo = newPaidTo }
            }
        }
    }
}