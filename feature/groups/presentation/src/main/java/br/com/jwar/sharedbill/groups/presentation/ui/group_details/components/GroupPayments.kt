package br.com.jwar.sharedbill.groups.presentation.ui.group_details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import br.com.jwar.sharedbill.groups.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.core.designsystem.theme.AppTheme
import br.com.jwar.sharedbill.testing.TestTags

@Composable
fun GroupPayments(
    modifier: Modifier = Modifier,
    group: GroupUiModel,
    listState: LazyListState
) {
    LazyColumn(
        modifier = modifier.testTag(TestTags.PaymentList),
        state = listState,
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.space_4)
    ) {
        items(group.payments.asList()) { payment ->
            GroupPaymentCard(payment, group)
        }
    }
}