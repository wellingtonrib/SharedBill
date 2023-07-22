package br.com.jwar.groups.presentation.screens.group_details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.com.jwar.groups.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.core.designsystem.theme.AppTheme
import br.com.jwar.sharedbill.core.designsystem.theme.VerticalSpacerMedium

@Composable
fun GroupPayments(
    group: GroupUiModel,
    listState: LazyListState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        VerticalSpacerMedium()
        LazyColumn(
            state = listState,
            verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.space_4)
        ) {
            items(group.payments) { payment ->
                GroupPaymentCard(payment, group)
            }
        }
    }
}