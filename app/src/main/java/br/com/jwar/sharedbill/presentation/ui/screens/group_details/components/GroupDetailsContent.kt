package br.com.jwar.sharedbill.presentation.ui.screens.group_details.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.Payment
import br.com.jwar.sharedbill.domain.model.User
import br.com.jwar.sharedbill.presentation.ui.screens.group_details.GroupDetailsContract.State
import br.com.jwar.sharedbill.presentation.ui.screens.group_details.GroupDetailsContract.State.Loaded
import br.com.jwar.sharedbill.presentation.ui.screens.group_details.GroupDetailsContract.State.Loading
import br.com.jwar.sharedbill.presentation.ui.screens.group_details.GroupDetailsContract.State.Error
import br.com.jwar.sharedbill.presentation.ui.theme.SharedBillTheme
import br.com.jwar.sharedbill.presentation.ui.widgets.EmptyContent
import br.com.jwar.sharedbill.presentation.ui.widgets.LoadingContent
import com.google.firebase.Timestamp
import java.text.NumberFormat
import java.text.SimpleDateFormat

@Composable
fun GroupDetailsContent(
    state: State,
    onNewExpenseClick: ()-> Unit = {},
    onManageClick: ()-> Unit = {},
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),) {
        when(state) {
            is Loading -> LoadingContent()
            is Loaded -> GroupsDetails(state.group, onNewExpenseClick, onManageClick)
            is Error -> EmptyContent()
        }
    }
}

@Composable
fun GroupsDetails(group: Group, onNewExpenseClick: ()-> Unit, onManageClick: ()-> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Group Details", style = MaterialTheme.typography.titleLarge, modifier = Modifier.weight(1f))
            Button(onClick = onManageClick) {
                Text(text = "Manage")
            }
        }
        Text(text = "Title: ${group.title}")
        Text(text = "Members: ${group.members.joinToString { it.name }}")
        Divider(modifier = Modifier.height(16.dp), color = Color.Transparent)
        LazyColumn {
            items(group.payments) {
                GroupPaymentCard(it)
            }
        }
        Button(onClick = onNewExpenseClick, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(text = "New expense")
        }
    }
}

@Composable
fun GroupPaymentCard(payment: Payment) {
    Column {
        Card(modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
                Text(text = payment.createdAt.format("dd/MM"))
                Divider(modifier = Modifier.width(16.dp), color = Color.Transparent)
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = payment.description)
                    Text(text = "Paid by ${payment.paidBy}", fontSize = 10.sp)
                }
                Divider(modifier = Modifier.width(16.dp), color = Color.Transparent)
                Text(text = payment.value.toCurrency())
            }
        }
        Divider(modifier = Modifier.height(16.dp), color = Color.Transparent)
    }
}

fun Timestamp.format(pattern: String = "dd/MM/yyyy"): String {
    return SimpleDateFormat(pattern).format(this.toDate())
}

fun String.toCurrency(): String {
    return NumberFormat.getCurrencyInstance().format(this.toDouble()).orEmpty()
}

@Preview
@Composable
fun PreviewGroupDetailsContent() {
    SharedBillTheme {
        Scaffold {
            GroupDetailsContent(state = Loaded(
                Group(
                    title = "Group One",
                    members = listOf(
                        User(name = "User One"),
                        User(name = "User Two"),
                        User(name = "User Three"),
                    ),
                    payments = listOf(
                        Payment(description = "Expense One", paidBy = "User One", value = "100", createdAt = Timestamp.now()),
                        Payment(description = "Expense Two", paidBy = "User One", value = "100", createdAt = Timestamp.now()),
                        Payment(description = "Expense Three", paidBy = "User One", value = "100", createdAt = Timestamp.now()),
                        Payment(description = "Expense Four", paidBy = "User One", value = "100", createdAt = Timestamp.now())
                    )
                )
            ))
        }
    }
}
