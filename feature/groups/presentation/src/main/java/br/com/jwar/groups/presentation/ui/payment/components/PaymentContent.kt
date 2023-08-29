package br.com.jwar.groups.presentation.ui.payment.components


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.groups.presentation.models.GroupMemberUiModel
import br.com.jwar.groups.presentation.models.PaymentUiModel
import br.com.jwar.groups.presentation.ui.payment.PaymentContract
import br.com.jwar.sharedbill.core.designsystem.components.AppTopBar
import br.com.jwar.sharedbill.core.designsystem.components.CloseNavigationIcon
import br.com.jwar.sharedbill.core.designsystem.components.Field
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.core.designsystem.theme.horizontalPaddingMedium
import br.com.jwar.sharedbill.core.utility.extensions.ifOrNull
import br.com.jwar.sharedbill.core.utility.extensions.orZero
import java.math.BigDecimal
import java.util.Date
import br.com.jwar.sharedbill.core.designsystem.R as DSR

@Composable
fun PaymentContent(
    modifier: Modifier = Modifier,
    state: PaymentContract.State,
    onSaveClick: (payment: PaymentUiModel) -> Unit = {},
    onNavigateBack: () -> Unit = {}
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val focusManager = remember { FocusRequester() }

    var description by remember(state.paymentUiModel) { mutableStateOf(state.paymentUiModel.description) }
    var value by remember(state.paymentUiModel) { mutableStateOf(state.paymentUiModel.value) }
    var dateTime by remember(state.paymentUiModel) { mutableLongStateOf(state.paymentUiModel.createdAt.time) }
    var paidBy by remember(state.paymentUiModel) { mutableStateOf(state.paymentUiModel.paidBy) }
    var paidTo by remember(state.paymentUiModel) { mutableStateOf(state.paymentUiModel.paidTo) }

    val sharedValue by remember(state.paymentUiModel) {
        derivedStateOf {
            if (paidTo.isEmpty()) BigDecimal.ZERO
            else value.toBigDecimalOrNull()?.div(paidTo.size.toBigDecimal()).orZero()
        }
    }

    LaunchedEffect(state.genericError) {
        state.genericError?.message?.asString(context)?.let { snackbarHostState.showSnackbar(it) }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            AppTopBar(
                navigationBack = onNavigateBack,
                navigationIcon = { CloseNavigationIcon(onNavigateBack) },
                title = state.title,
                actions = {
                    IconButton(onClick = {
                        onSaveClick(
                            PaymentUiModel(
                                description = description,
                                value = value,
                                paidBy = paidBy,
                                paidTo = paidTo,
                                createdAt = Date(dateTime),
                            )
                        )
                        keyboardController?.hide()
                    }) {
                        Icon(Icons.Filled.Done, stringResource(id = DSR.string.description_done))
                    }
                }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .horizontalPaddingMedium()
                .padding(innerPadding),
        ) {
            state.inputFields.forEach { field ->
                Field {
                    when(field) {
                        is PaymentContract.Field.DescriptionField -> {
                            PaymentDescriptionField(
                                focusRequester = ifOrNull(field.requestFocus) { focusManager },
                                imeAction = ImeAction.Next,
                                description = description,
                                error = field.error,
                            ) { newDescription -> description = newDescription.text }
                        }
                        is PaymentContract.Field.ValueField -> {
                            PaymentValueField(
                                focusRequester = ifOrNull(field.requestFocus) { focusManager },
                                imeAction = ImeAction.Next,
                                value = value,
                                error = field.error,
                            ) { newValue -> value = newValue.text }
                        }
                        is PaymentContract.Field.DateField -> {
                            PaymentDateField(
                                imeAction = ImeAction.Done,
                                dateTime = dateTime,
                                error = field.error,
                            ) { newDateTime -> dateTime = newDateTime }
                        }
                        is PaymentContract.Field.PaidByField -> {
                            PaymentPaidByField(
                                paidBy = paidBy,
                                paidByOptions = field.options,
                                error = field.error,
                            ) { newPaidBy -> paidBy = newPaidBy }
                        }
                        is PaymentContract.Field.PaidToField -> {
                            PaymentPaidToField(
                                paidTo = paidTo,
                                sharedValue = sharedValue,
                                paidToOptions = field.options,
                                isExpense = field.isMultiSelect,
                                error = field.error,
                            ) { newPaidTo -> paidTo = newPaidTo }
                        }
                    }
                }
                if (field is PaymentContract.Focusable && field.requestFocus) {
                    LaunchedEffect(Unit) { focusManager.requestFocus() }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewPaymentContent() {
    SharedBillTheme {
        PaymentContent(
            state = PaymentContract.State(
                isLoading = false,
                title = "New payment",
                inputFields = listOf(
                    PaymentContract.Field.DescriptionField(),
                    PaymentContract.Field.ValueField(),
                    PaymentContract.Field.DateField(),
                    PaymentContract.Field.PaidByField(
                        options = listOf(
                            GroupMemberUiModel.sample(),
                            GroupMemberUiModel.sample()
                        ).associateWith { true }
                    ),
                    PaymentContract.Field.PaidToField(
                        options = listOf(
                            GroupMemberUiModel.sample(),
                            GroupMemberUiModel.sample()
                        )
                    )
                ),
                paymentUiModel = PaymentUiModel.sample()
            ),
            onSaveClick = {},
        )
    }
}
