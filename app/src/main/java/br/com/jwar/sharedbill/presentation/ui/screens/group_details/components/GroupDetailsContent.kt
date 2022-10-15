package br.com.jwar.sharedbill.presentation.ui.screens.group_details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.presentation.ui.screens.group_details.GroupDetailsContract.State
import br.com.jwar.sharedbill.presentation.ui.screens.group_details.GroupDetailsContract.State.Error
import br.com.jwar.sharedbill.presentation.ui.screens.group_details.GroupDetailsContract.State.Loaded
import br.com.jwar.sharedbill.presentation.ui.screens.group_details.GroupDetailsContract.State.Loading
import br.com.jwar.sharedbill.presentation.ui.theme.SharedBillTheme
import br.com.jwar.sharedbill.presentation.ui.generic_components.EmptyContent
import br.com.jwar.sharedbill.presentation.ui.generic_components.LoadingContent
import br.com.jwar.sharedbill.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.presentation.ui.theme.fillMaxWidthPaddingMedium

@Composable
fun GroupDetailsContent(
    state: State,
    onNewPaymentClick: (String)-> Unit = {},
    onManageClick: ()-> Unit = {},
) {
    Column(modifier = Modifier.fillMaxWidthPaddingMedium()) {
        when(state) {
            is Loading -> LoadingContent()
            is Loaded -> GroupsDetails(
                group = state.group,
                onManageClick = onManageClick,
                onNewPaymentClick = { onNewPaymentClick(state.group.id) }
            )
            is Error -> EmptyContent()
        }
    }
}

@Preview
@Composable
fun PreviewGroupDetailsContent() {
    SharedBillTheme {
        Scaffold {
            GroupDetailsContent(state = Loaded(GroupUiModel.sample()))
        }
    }
}
