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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.Payment
import br.com.jwar.sharedbill.domain.model.User
import br.com.jwar.sharedbill.presentation.ui.screens.payment.PaymentContract.State
import br.com.jwar.sharedbill.presentation.ui.theme.SharedBillTheme
import br.com.jwar.sharedbill.presentation.ui.widgets.ErrorContent
import br.com.jwar.sharedbill.presentation.ui.widgets.LoadingContent
import br.com.jwar.sharedbill.presentation.ui.widgets.SelectDialog

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

    var description by remember { mutableStateOf("") }
    var value by remember { mutableStateOf("") }

    val paidBySelection = remember { mutableStateOf<User?>(null) }
    val isPaidBySelecting = PaymentPaidBySelector(group, paidBySelection)

    val paidToSelection = remember { mutableStateOf<List<User>>(emptyList()) }
    val isPaidToSelecting = PaymentPaidToSelector(group, paidToSelection)

    Column {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            value = description,
            label = { Text(text = "Description") },
            placeholder = { Text(text = "Ex. Hotel") },
            onValueChange = { description = it }
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            value = value,
            label = { Text(text = "Value") },
            placeholder = { Text(text = "R$0,00") },
            onValueChange = { value = it }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row (verticalAlignment = Alignment.CenterVertically){
            Text(text = "Paid by: ")
            Button(
                onClick = { isPaidBySelecting.value = true },
                modifier = Modifier.weight(1f))
            {
                Text(text = "You")
            }
        }
        Row (verticalAlignment = Alignment.CenterVertically){
           Text(text = "Paid to: ")
            Button(
                onClick = { isPaidToSelecting.value = true },
                modifier = Modifier.weight(1f))
            {
                Text(text = "All")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            onSendPaymentClick(
                Payment(
                    description = description,
                    value = value,
                    paidBy = paidBySelection.value ?: group.members.first(),
                    paidTo = paidToSelection.value.ifEmpty { group.members }
                ),
                group
            )
        }) {
            Text(text = "Send payment")
        }
    }
}

@Composable
private fun PaymentPaidToSelector(
    group: Group,
    paidToSelection: MutableState<List<User>>
): MutableState<Boolean> {
    val isPaidToSelecting = remember { mutableStateOf(false) }
    if (isPaidToSelecting.value) {
        SelectDialog(
            title = "Paid To",
            message = "Select the members",
            options = group.members.associateWith { true },
            onDismiss = {
                isPaidToSelecting.value = false
            },
            onSelect = {
                isPaidToSelecting.value = false
                paidToSelection.value = it
            }
        )
    }
    return isPaidToSelecting
}

@Composable
private fun PaymentPaidBySelector(
    group: Group,
    paidBySelection: MutableState<User?>
): MutableState<Boolean> {
    val isPaidBySelecting = remember { mutableStateOf(false) }
    if (isPaidBySelecting.value) {
        SelectDialog(
            title = "Paid By",
            message = "Select the payer",
            options = group.members.associateWith { it == group.members.first() },
            isMultiChoice = false,
            onDismiss = {
                isPaidBySelecting.value = false
            },
            onSelect = {
                isPaidBySelecting.value = false
                paidBySelection.value = it.first()
            }
        )
    }
    return isPaidBySelecting
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