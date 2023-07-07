package br.com.jwar.groups.presentation.screens.group_details.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.groups.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.core.designsystem.components.Title
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
                expanded = listState.isScrollingUp(),
                icon = { Icon(Icons.Filled.Add, stringResource(R.string.label_payment_new)) },
                text = { Text(text = stringResource(R.string.label_payment_new)) },
            )
        },
        floatingActionButtonPosition = FabPosition.End,
    ) { contentPadding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)) {
            if (group.payments.isEmpty()) {
                GroupPaymentsEmpty(Modifier.fillMaxSize())
            } else {
                GroupBalance(group)
                VerticalSpacerMedium()
                Divider()
                VerticalSpacerMedium()
                Title(stringResource(R.string.label_group_payments))
                VerticalSpacerMedium()
                LazyColumn(
                    state = listState,
                ) {
                    items(group.payments) { payment ->
                        GroupPaymentCard(payment, group)
                    }
                }
            }
        }
    }
}

@Composable
private fun LazyListState.isScrollingUp() : Boolean {
    var previousIndex by remember(this) {
        mutableStateOf(firstVisibleItemIndex)
    }
    var previousScrollOffset by remember(this) {
        mutableStateOf(firstVisibleItemScrollOffset)
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun PreviewGroupDetails() {
    SharedBillTheme {
        Scaffold {
            GroupsDetails(GroupUiModel.sample())
        }
    }
}
