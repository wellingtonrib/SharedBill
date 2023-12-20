package br.com.jwar.sharedbill.groups.presentation.ui.payment

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.core.designsystem.components.LoadingContent
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.groups.presentation.models.GroupMemberUiModel
import br.com.jwar.sharedbill.groups.presentation.ui.payment.components.PaymentContent
import com.google.common.collect.ImmutableSet

@Composable
fun PaymentScreen(
    state: PaymentContract.State,
    onDescriptionChange: (String) -> Unit = {},
    onValueChange: (String) -> Unit = {},
    onDateChange: (Long) -> Unit = {},
    onPaidByChange: (GroupMemberUiModel) -> Unit = {},
    onPaidToChange: (ImmutableSet<GroupMemberUiModel>) -> Unit = {},
    onSaveClick: () -> Unit = {},
    onNavigateBack: () -> Unit = {},
) {
    when {
        state.isLoading -> LoadingContent()
        else -> PaymentContent(
            state = state,
            onSaveClick = onSaveClick,
            onDescriptionChange = onDescriptionChange,
            onValueChange = onValueChange,
            onDateChange = onDateChange,
            onPaidByChange = onPaidByChange,
            onPaidToChange = onPaidToChange,
            onNavigateBack = onNavigateBack
        )
    }
}

@Preview
@Composable
fun PreviewPaymentContent() {
    SharedBillTheme {
        PaymentScreen(
            state = PaymentContract.State(),
            onSaveClick = {},
        )
    }
}