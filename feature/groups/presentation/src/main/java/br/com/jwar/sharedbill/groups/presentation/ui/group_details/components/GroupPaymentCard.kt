package br.com.jwar.sharedbill.groups.presentation.ui.group_details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.core.designsystem.theme.AppTheme
import br.com.jwar.sharedbill.core.designsystem.theme.HorizontalSpacerMedium
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.core.designsystem.theme.paddingMedium
import br.com.jwar.sharedbill.core.utility.extensions.DATE_FORMAT_DEFAULT
import br.com.jwar.sharedbill.core.utility.extensions.format
import br.com.jwar.sharedbill.groups.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.groups.presentation.models.PaymentUiModel
import br.com.jwar.sharedbill.groups.presentation.models.getMessage

@Composable
fun GroupPaymentCard(
    modifier: Modifier = Modifier,
    payment: PaymentUiModel,
    group: GroupUiModel,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        onClick = { onClick() }
    ) {
        Row(
            modifier = Modifier.paddingMedium()
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = payment.description,
                    style = AppTheme.typo.titleMedium
                )
                Text(
                    text = payment.getMessage(group),
                    style = AppTheme.typo.bodySmall
                )
            }
            HorizontalSpacerMedium()
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = payment.value,
                    style = AppTheme.typo.titleMedium
                )
                Text(
                    text = payment.createdAt.format(DATE_FORMAT_DEFAULT),
                    style = AppTheme.typo.bodySmall
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGroupPaymentCard(){
    SharedBillTheme {
        GroupPaymentCard(
            payment = PaymentUiModel.sample(),
            group = GroupUiModel.sample()
        ) {}
    }
}