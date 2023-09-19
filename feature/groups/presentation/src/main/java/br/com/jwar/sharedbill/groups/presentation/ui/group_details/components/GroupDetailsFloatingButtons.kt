package br.com.jwar.sharedbill.groups.presentation.ui.group_details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import br.com.jwar.sharedbill.groups.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.core.designsystem.theme.AppTheme
import br.com.jwar.sharedbill.groups.domain.model.PaymentType
import br.com.jwar.sharedbill.groups.presentation.R

@Composable
fun GroupDetailsFloatingButtons(
    group: GroupUiModel,
    listState: LazyListState,
    onNewPaymentClick: (PaymentType) -> Unit,
) {
    val showSettlement by remember { derivedStateOf { group.members.size > 1 } }
    val firstIndexVisible by remember {
        derivedStateOf { listState.firstVisibleItemIndex == 0 }
    }

    if (showSettlement) {
        NewPaymentGroupedButtons(firstIndexVisible, onNewPaymentClick)
    } else {
        NewExpenseButton(firstIndexVisible, onNewPaymentClick)
    }
}

@Composable
private fun NewPaymentGroupedButtons(
    firstIndexVisible: Boolean,
    onNewPaymentClick: (PaymentType) -> Unit
) {
    var actionButtonsVisible by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.space_4)
    ) {
        if (actionButtonsVisible) {
            NewExpenseButton(true, onNewPaymentClick)
            NewSettlementButton(true, onNewPaymentClick)
        }
        ExtendedFloatingActionButton(
            onClick = { actionButtonsVisible = !actionButtonsVisible },
            expanded = firstIndexVisible && actionButtonsVisible.not() ,
            icon = { Icon(Icons.Filled.Add, stringResource(R.string.label_payment_new)) },
            text = { Text(stringResource(R.string.label_payment_new)) },
        )
    }
}

@Composable
private fun NewSettlementButton(
    expanded: Boolean,
    onNewPaymentClick: (PaymentType) -> Unit
) {
    ExtendedFloatingActionButton(
        onClick = { onNewPaymentClick(PaymentType.SETTLEMENT) },
        expanded = expanded,
        icon = {
            Icon(
                Icons.Outlined.ThumbUp,
                stringResource(R.string.label_payment_new_settlement)
            )
        },
        text = { Text(stringResource(R.string.label_payment_new_settlement)) },
    )
}

@Composable
private fun NewExpenseButton(
    expanded: Boolean,
    onNewPaymentClick: (PaymentType) -> Unit
) {
    ExtendedFloatingActionButton(
        onClick = { onNewPaymentClick(PaymentType.EXPENSE) },
        expanded = expanded,
        icon = {
            Icon(
                Icons.Outlined.ShoppingCart,
                stringResource(R.string.label_payment_new_expense)
            )
        },
        text = { Text(stringResource(R.string.label_payment_new_expense)) },
    )
}