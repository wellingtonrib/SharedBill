package br.com.jwar.sharedbill.presentation.ui.screens.payment.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.Payment
import br.com.jwar.sharedbill.presentation.ui.screens.payment.PaymentContract.State
import br.com.jwar.sharedbill.presentation.ui.theme.SharedBillTheme
import br.com.jwar.sharedbill.presentation.ui.widgets.ErrorContent
import br.com.jwar.sharedbill.presentation.ui.widgets.LoadingContent
import kotlin.random.Random

@Composable
fun PaymentContent(
    state: State,
    onSendPaymentClick: (Payment, Group) -> Unit = {_,_->},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when(state) {
            is State.Loading -> LoadingContent()
            is State.Loaded -> PaymentForm(state.group, onSendPaymentClick)
            is State.Error -> ErrorContent(state.message)
        }
    }
}

@Composable
fun PaymentForm(group: Group, onSendPaymentClick: (Payment, Group) -> Unit) {
    Column() {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            value = "",
            label = { Text(text = "Description") },
            placeholder = { Text(text = "Ex. Hotel") },
            onValueChange = { }
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            value = "",
            label = { Text(text = "Value") },
            placeholder = { Text(text = "R$0,00") },
            onValueChange = { }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row (verticalAlignment = Alignment.CenterVertically){
            Text(text = "Paid by: ")
            Button(onClick = { /*TODO*/ }, modifier = Modifier.weight(1f)) {
                Text(text = "You")
            }
        }
        Row (verticalAlignment = Alignment.CenterVertically){
           Text(text = "Paid to: ")
            Button(onClick = { /*TODO*/ }, modifier = Modifier.weight(1f)) {
                Text(text = "All")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            onSendPaymentClick(
                Payment(
                    description = "Payment ${group.payments.size + 1}",
                    value = Random.nextInt(50, 100).toString(),
                    paidBy = group.members[Random.nextInt(group.members.size - 1)],
                    paidTo = group.members
                ),
                group
            )
        }) {
            Text(text = "Send payment")
        }
    }
}

@Preview
@Composable
fun PreviewPaymentContent() {
    SharedBillTheme {
        Scaffold {
            PaymentContent(state = State.Loaded(Group.fake()))
        }
    }
}