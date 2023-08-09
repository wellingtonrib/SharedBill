package br.com.jwar.groups.presentation.ui.group_details.components

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.jwar.groups.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.core.designsystem.components.AppTopBar
import br.com.jwar.sharedbill.core.designsystem.components.EmptyContent
import br.com.jwar.sharedbill.core.designsystem.components.SegmentedControl
import br.com.jwar.sharedbill.core.designsystem.theme.AppTheme
import br.com.jwar.sharedbill.groups.domain.model.PaymentType
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.groups.presentation.R
import kotlinx.coroutines.launch

private const val PAGE_PAYMENTS = 0
private const val PAGE_BALANCE = 1

typealias Page = Pair<ImageVector, String>

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GroupsDetailsContent(
    group: GroupUiModel,
    onNewPaymentClick: (PaymentType)-> Unit = {},
    onEditClick: () -> Unit = {},
    onShareBalance: (String) -> Unit = {},
    onNavigateBack: () -> Unit = {},
) {
    val listState = rememberLazyListState()
    val pages = listOf(
        ImageVector.vectorResource(R.drawable.ic_receipt) to stringResource(R.string.label_group_payments),
        ImageVector.vectorResource(R.drawable.ic_balance) to stringResource(R.string.label_group_balance),
    )
    val pagerState = rememberPagerState(
        initialPage = PAGE_PAYMENTS,
        initialPageOffsetFraction = 0f
    ) { pages.size }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            AppTopBar(
                title = group.title,
                navigationBack = onNavigateBack,
                actions = {
                    IconButton(onClick = onEditClick) {
                        Icon(Icons.Filled.Edit, stringResource(R.string.label_group_manage))
                    }
                }
            )
        },
        floatingActionButton = {
            GroupDetailsFloatingButtons(
                listState = listState,
                onNewPaymentClick = onNewPaymentClick
            )
        },
        floatingActionButtonPosition = FabPosition.End,
    ) { contentPadding ->
        Box(
            modifier = Modifier.padding(contentPadding)
        ) {
            if (group.payments.isEmpty()) {
                EmptyContent(
                    image = painterResource(R.drawable.group_details_empty_img),
                    message = stringResource(R.string.message_no_expenses),
                )
            } else {
                Column(
                    modifier = Modifier.padding(horizontal = AppTheme.dimens.space_8),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SegmentedControl(
                        cornerRadius = 40,
                        items = pages.map { it.second },
                        onItemSelection = { index -> scope.launch { pagerState.scrollToPage(index) } }
                    )
                    HorizontalPager(
                        state = pagerState,
                        userScrollEnabled = false
                    ) { page ->
                        when (page) {
                            PAGE_PAYMENTS -> GroupPayments(group, listState)
                            PAGE_BALANCE -> GroupBalance(group, onShareBalance)
                        }
                    }
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun PreviewGroupDetails() {
    SharedBillTheme {
        Scaffold {
            GroupsDetailsContent(GroupUiModel.sample())
        }
    }
}
