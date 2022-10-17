package br.com.jwar.sharedbill.presentation.ui.screens.payment.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import br.com.jwar.sharedbill.R
import br.com.jwar.sharedbill.presentation.models.UserUiModel
import br.com.jwar.sharedbill.presentation.ui.generic_components.SelectDialog
import br.com.jwar.sharedbill.presentation.ui.theme.horizontalSpaceMedium

@Composable
fun PaymentPaidToField(
    paidToSelection: MutableState<List<UserUiModel>>,
    members: List<UserUiModel>
) {
    val isPaidToSelecting = remember { mutableStateOf(false) }
    if (isPaidToSelecting.value) {
        SelectDialog(
            title = stringResource(R.string.label_payment_paid_to),
            message = stringResource(R.string.placeholder_payment_paid_to),
            options = members.associateWith { paidToSelection.value.contains(it) },
            onDismiss = {
                isPaidToSelecting.value = false
            },
            onSelect = {
                isPaidToSelecting.value = false
                paidToSelection.value = it
            }
        )
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = stringResource(R.string.label_payment_paid_to))
        Spacer(modifier = Modifier.horizontalSpaceMedium())
        OutlinedButton(
            onClick = { isPaidToSelecting.value = true },
            modifier = Modifier.weight(1f)
        )
        {
            Text(text = paidToSelection.value.joinToString { it.name })
        }
    }
}