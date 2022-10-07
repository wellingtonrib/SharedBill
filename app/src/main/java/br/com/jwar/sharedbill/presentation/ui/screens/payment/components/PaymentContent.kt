package br.com.jwar.sharedbill.presentation.ui.screens.payment.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
    Surface {
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
            PaymentContent(state = State.Loading)
        }
    }
}