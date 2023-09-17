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
import androidx.compose.runtime.mutableIntStateOf
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

    if (showSettlement) {
        NewPaymentGroupedButtons(listState, onNewPaymentClick)
    } else {
        NewExpenseButton(listState, onNewPaymentClick)
    }
}

@Composable
private fun NewPaymentGroupedButtons(
    listState: LazyListState,
    onNewPaymentClick: (PaymentType) -> Unit
) {
    var actionButtonsVisible by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.space_4)
    ) {
        if (actionButtonsVisible) {
            NewExpenseButton(listState, onNewPaymentClick)
            NewSettlementButton(onNewPaymentClick)
        }
        ExtendedFloatingActionButton(
            onClick = { actionButtonsVisible = !actionButtonsVisible },
            expanded = listState.isScrollingUp() && !actionButtonsVisible,
            icon = { Icon(Icons.Filled.Add, stringResource(R.string.label_payment_new)) },
            text = { Text(stringResource(R.string.label_payment_new)) },
        )
    }
}

@Composable
private fun NewSettlementButton(
    onNewPaymentClick: (PaymentType) -> Unit
) {
    ExtendedFloatingActionButton(
        onClick = { onNewPaymentClick(PaymentType.SETTLEMENT) },
        expanded = true,
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
private fun NewExpenseButton(listState: LazyListState, onNewPaymentClick: (PaymentType) -> Unit) {
    ExtendedFloatingActionButton(
        onClick = { onNewPaymentClick(PaymentType.EXPENSE) },
        expanded = listState.isScrollingUp(),
        icon = {
            Icon(
                Icons.Outlined.ShoppingCart,
                stringResource(R.string.label_payment_new_expense)
            )
        },
        text = { Text(stringResource(R.string.label_payment_new_expense)) },
    )
}

@Composable
private fun LazyListState.isScrollingUp() : Boolean {
    var previousIndex by remember(this) {
        mutableIntStateOf(firstVisibleItemIndex)
    }
    var previousScrollOffset by remember(this) {
        mutableIntStateOf(firstVisibleItemScrollOffset)
    }
    return remember(this) {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex > firstVisibleItemIndex
            } else {
                previousScrollOffset >= firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }.value
    }
}