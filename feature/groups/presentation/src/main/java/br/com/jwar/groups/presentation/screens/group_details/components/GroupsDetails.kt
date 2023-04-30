package br.com.jwar.groups.presentation.screens.group_details.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.groups.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.core.designsystem.theme.VerticalSpacerMedium
import br.com.jwar.sharedbill.groups.presentation.R

@Composable
fun GroupsDetails(
    group: GroupUiModel,
    onNewPaymentClick: ()-> Unit = {},
) {
    val listState = rememberLazyListState()
    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { onNewPaymentClick() },
                expanded = listState.firstVisibleItemIndex == 0,
                icon = { Icon(Icons.Filled.Add, stringResource(R.string.label_payment_new)) },
                text = { Text(text = stringResource(R.string.label_payment_new)) },
            )
        },
        floatingActionButtonPosition = FabPosition.End,
    ) {
        LazyColumn(
            state = listState
        ) {
            item {
                GroupTitle(group)
            }
            item {
                GroupBalance(group)
            }
            item {
                VerticalSpacerMedium()
            }
            items(group.payments) { payment ->
                GroupPaymentCard(payment, group)
            }
            item {
                if (group.payments.isEmpty()) {
                    GroupPaymentsEmpty(Modifier.fillParentMaxHeight())
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewGroupDetails() {
    SharedBillTheme {
        Scaffold {
            GroupsDetails(br.com.jwar.groups.presentation.models.GroupUiModel.sample().copy(payments = emptyList()))
        }
    }
}
