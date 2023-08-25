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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import br.com.jwar.groups.presentation.models.PaymentUiError
import br.com.jwar.groups.presentation.models.PaymentUiModel
import br.com.jwar.groups.presentation.ui.payment.PaymentContract
import br.com.jwar.sharedbill.core.designsystem.components.AppTopBar
import br.com.jwar.sharedbill.core.designsystem.components.CloseNavigationIcon
import br.com.jwar.sharedbill.core.designsystem.components.Field
import br.com.jwar.sharedbill.core.designsystem.theme.AppTheme
import br.com.jwar.sharedbill.core.designsystem.theme.paddingMedium
import br.com.jwar.sharedbill.core.utility.extensions.format
import br.com.jwar.sharedbill.core.utility.extensions.orZero
import br.com.jwar.sharedbill.core.utility.extensions.parse
import br.com.jwar.sharedbill.groups.domain.model.PaymentType
import br.com.jwar.sharedbill.groups.presentation.R
import java.math.BigDecimal
import java.util.Date
import br.com.jwar.sharedbill.core.designsystem.R as DSR

@Composable
fun ExpensePaymentContent(
    modifier: Modifier = Modifier,
    state: PaymentContract.State.Loaded,
    onSaveClick: (payment: PaymentUiModel) -> Unit,
    onNavigateBack: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val focusManager = remember { FocusRequester() }

    var description by remember { mutableStateOf("") }
    var value by remember { mutableStateOf("") }
    var dateTime by remember { mutableStateOf(Date().format()) }
    var paidBy by remember { mutableStateOf(state.paidByDefault) }
    var paidTo by remember { mutableStateOf(state.paidToOptions) }

    var descriptionError by remember { mutableStateOf<PaymentUiError.InvalidDescriptionError?>(null) }
    var valueError by remember { mutableStateOf<PaymentUiError.InvalidValueError?>(null) }
    var dateError by remember { mutableStateOf<PaymentUiError.InvalidDateError?>(null) }

    val sharedValue by remember {
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
        modifier = modifier,
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
                                createdAt = dateTime.parse(),
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
        Box(modifier = modifier.padding(innerPadding)) {
            Column(
                modifier = modifier.paddingMedium(),
                verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.space_4)
            ) {
                Field {
                    LaunchedEffect(Unit) {
                        focusManager.requestFocus()
                    }
                    PaymentDescriptionField(
                        focusRequester = focusManager,
                        imeAction = ImeAction.Next,
                        description = description,
                        error = descriptionError
                    ) { newDescription ->
                        description = newDescription.text; descriptionError = null
                    }
                }
                Field {
                    PaymentValueField(
                        imeAction = ImeAction.Next,
                        value = value,
                        error = valueError
                    ) { newValue -> value = newValue; valueError = null }
                }
                Field {
                    PaymentDateField(
                        imeAction = ImeAction.Done,
                        date = dateTime,
                        error = dateError
                    ) { newDate -> dateTime = newDate; dateError = null }
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
                        sharedValue = sharedValue,
                        paidToOptions = state.paidToOptions,
                        isExpense = true,
                    ) { newPaidTo -> paidTo = newPaidTo }
                }
            }
        }
    }
}

