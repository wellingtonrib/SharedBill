package br.com.jwar.sharedbill.groups.presentation.ui.details

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.core.designsystem.components.ErrorContent
import br.com.jwar.sharedbill.core.designsystem.components.LoadingContent
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.groups.domain.model.PaymentType
import br.com.jwar.sharedbill.groups.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.groups.presentation.ui.details.GroupDetailsContract.State
import br.com.jwar.sharedbill.groups.presentation.ui.details.GroupDetailsContract.State.Error
import br.com.jwar.sharedbill.groups.presentation.ui.details.GroupDetailsContract.State.Loaded
import br.com.jwar.sharedbill.groups.presentation.ui.details.GroupDetailsContract.State.Loading
import br.com.jwar.sharedbill.groups.presentation.ui.details.components.GroupsDetailsContent

@Composable
fun GroupDetailsScreen(
    state: State,
    onNavigateBack: () -> Unit = {},
    onNewPaymentClick: (PaymentType) -> Unit = {},
    onEditClick: () -> Unit = {},
    onShareBalance: (String) -> Unit = {},
    onDeletePayment: (String, String) -> Unit = { _, _ -> },
) {
    when (state) {
        is Loading -> LoadingContent()
        is Loaded -> GroupsDetailsContent(
            group = state.uiModel,
            onNewPaymentClick = { onNewPaymentClick(it) },
            onEditClick = onEditClick,
            onShareBalance = onShareBalance,
            onDeletePayment = onDeletePayment,
            onNavigateBack = onNavigateBack
        )
        is Error -> ErrorContent()
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun PreviewGroupDetailsContent() {
    SharedBillTheme {
        Scaffold {
            GroupDetailsScreen(
                state = Loaded(GroupUiModel.sample()),
            )
        }
    }
}
