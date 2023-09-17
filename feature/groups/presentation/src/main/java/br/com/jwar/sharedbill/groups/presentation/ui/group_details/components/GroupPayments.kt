package br.com.jwar.sharedbill.groups.presentation.ui.group_details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import br.com.jwar.sharedbill.groups.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.core.designsystem.theme.AppTheme

@Composable
fun GroupPayments(
    group: GroupUiModel,
    listState: LazyListState
) {
    LazyColumn(
        state = listState,
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.space_4)
    ) {
        items(group.payments.asList()) { payment ->
            GroupPaymentCard(payment, group)
        }
    }
}