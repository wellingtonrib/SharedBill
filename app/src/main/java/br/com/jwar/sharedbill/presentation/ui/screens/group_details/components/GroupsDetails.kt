package br.com.jwar.sharedbill.presentation.ui.screens.group_details.components

import androidx.compose.foundation.layout.Spacer
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
import br.com.jwar.sharedbill.R
import br.com.jwar.sharedbill.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.presentation.ui.theme.SharedBillTheme
import br.com.jwar.sharedbill.presentation.ui.theme.verticalSpaceMedium

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
                Spacer(modifier = Modifier.verticalSpaceMedium())
            }
            item {
                GroupBalance(group)
                Spacer(modifier = Modifier.verticalSpaceMedium())
            }
            items(group.payments) { payment ->
                GroupPaymentCard(payment, group)
            }
        }
    }
}

@Preview
@Composable
fun PreviewGroupDetails() {
    SharedBillTheme {
        Scaffold {
            GroupsDetails(GroupUiModel.sample())
        }
    }
}
