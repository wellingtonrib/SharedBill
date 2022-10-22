package br.com.jwar.sharedbill.presentation.ui.screens.group_details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
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
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            item {
                GroupInfo(group)
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
        NewExpenseButton(onNewPaymentClick)
    }
}

@Composable
private fun NewExpenseButton(onNewPaymentClick: () -> Unit) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = onNewPaymentClick,
    ) {
        Text(text = stringResource(R.string.label_payment_new))
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
