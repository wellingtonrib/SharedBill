package br.com.jwar.sharedbill.groups.presentation.ui.group_details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import br.com.jwar.sharedbill.core.designsystem.components.InfoDialog
import br.com.jwar.sharedbill.groups.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.core.designsystem.theme.AppTheme
import br.com.jwar.sharedbill.groups.presentation.R
import br.com.jwar.sharedbill.groups.presentation.models.PaymentUiModel
import br.com.jwar.sharedbill.groups.presentation.models.getInfo
import br.com.jwar.sharedbill.testing.TestTags

@Composable
fun GroupPayments(
    modifier: Modifier = Modifier,
    group: GroupUiModel,
    listState: LazyListState,
    onDeletePayment: (String) -> Unit,
) {
    var selectedPayment by remember { mutableStateOf<PaymentUiModel?>(null) }
    if (selectedPayment != null) {
        InfoDialog(
            image = null,
            title = stringResource(R.string.label_payment_detail),
            message = selectedPayment?.getInfo().orEmpty(),
            onDismiss = { selectedPayment = null },
            onAction = { selectedPayment = null }
        )
    }

    LazyColumn(
        modifier = modifier.testTag(TestTags.PaymentList),
        state = listState,
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.space_4)
    ) {
        items(group.payments.asList()) { payment ->
            GroupPaymentCard(
                payment = payment,
                group = group
            ) {
//                selectedPayment = payment
                onDeletePayment(payment.id)
            }
        }
    }
}
