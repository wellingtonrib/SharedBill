package br.com.jwar.sharedbill.groups.presentation.ui.payment.components


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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.groups.presentation.models.GroupMemberUiModel
import br.com.jwar.sharedbill.groups.presentation.ui.payment.PaymentContract
import br.com.jwar.sharedbill.core.designsystem.components.AppTopBar
import br.com.jwar.sharedbill.core.designsystem.components.CloseNavigationIcon
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.core.designsystem.theme.horizontalPaddingMedium
import br.com.jwar.sharedbill.core.designsystem.util.LogCompositions
import br.com.jwar.sharedbill.testing.TestTags
import com.google.common.collect.ImmutableMap
import com.google.common.collect.ImmutableSet
import br.com.jwar.sharedbill.core.designsystem.R as DSR

@Composable
fun PaymentContent(
    modifier: Modifier = Modifier,
    state: PaymentContract.State,
    onDescriptionChange: (String) -> Unit = {},
    onValueChange: (String) -> Unit = {},
    onDateChange: (Long) -> Unit = {},
    onPaidByChange: (GroupMemberUiModel) -> Unit = {},
    onPaidToChange: (ImmutableSet<GroupMemberUiModel>) -> Unit = {},
    onSaveClick: () -> Unit = {},
    onNavigateBack: () -> Unit = {}
) {
    LogCompositions("PaymentContent")

    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.genericError) {
        state.genericError?.message?.asString(context)?.let { snackbarHostState.showSnackbar(it) }
    }

    Scaffold(
        modifier = modifier.testTag(TestTags.PaymentContent),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            AppTopBar(
                navigationBack = onNavigateBack,
                navigationIcon = { CloseNavigationIcon(onNavigateBack) },
                title = stringResource(state.titleRes),
                actions = {
                    IconButton(onClick = {
                        onSaveClick()
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
            state.visibleFields.forEachIndexed { index, field ->
                when(field) {
                    is PaymentContract.Field.DescriptionField -> {
                        PaymentDescriptionField(
                            imeAction = ImeAction.Next,
                            description = field.value,
                            error = field.error,
                            onValueChange = onDescriptionChange
                        )
                    }
                    is PaymentContract.Field.ValueField -> {
                        PaymentValueField(
                            autoFocusable = index == 0,
                            imeAction = ImeAction.Next,
                            value = field.value,
                            error = field.error,
                            onValueChange = onValueChange
                        )
                    }
                    is PaymentContract.Field.DateField -> {
                        PaymentDateField(
                            imeAction = ImeAction.Done,
                            dateTime = field.value,
                            error = field.error,
                            onValueChange = onDateChange
                        )
                    }
                    is PaymentContract.Field.PaidByField -> {
                        PaymentPaidByField(
                            value = field.value,
                            options = field.options,
                            error = field.error,
                            onValueChange = onPaidByChange
                        )
                    }
                    is PaymentContract.Field.PaidToField -> {
                        PaymentPaidToField(
                            value = field.value,
                            sharedValue = field.sharedValue,
                            options = field.options,
                            isMultiSelect = field.isMultiSelect,
                            error = field.error,
                            onValueChange = onPaidToChange
                        )
                    }
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
                inputFields = ImmutableSet.of(
                    PaymentContract.Field.DescriptionField(),
                    PaymentContract.Field.ValueField(),
                    PaymentContract.Field.DateField(),
                    PaymentContract.Field.PaidByField(
                        options = ImmutableMap.copyOf(
                            listOf(
                                GroupMemberUiModel.sample(),
                                GroupMemberUiModel.sample()
                            ).associateWith { true }
                        )
                    ),
                    PaymentContract.Field.PaidToField(
                        options = ImmutableSet.of(
                            GroupMemberUiModel.sample(),
                            GroupMemberUiModel.sample()
                        )
                    )
                ),
            ),
            onSaveClick = {},
        )
    }
}
