package br.com.jwar.sharedbill.groups.presentation.ui.details.components

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.jwar.sharedbill.core.designsystem.components.AppTopBar
import br.com.jwar.sharedbill.core.designsystem.components.EmptyContent
import br.com.jwar.sharedbill.core.designsystem.components.SegmentedControl
import br.com.jwar.sharedbill.core.designsystem.theme.AppTheme
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.core.designsystem.theme.VerticalSpacerSmall
import br.com.jwar.sharedbill.groups.domain.model.PaymentType
import br.com.jwar.sharedbill.groups.presentation.R
import br.com.jwar.sharedbill.groups.presentation.models.GroupUiModel
import br.com.jwar.sharedbill.testing.TestTags
import kotlinx.coroutines.launch

private const val PAGE_PAYMENTS = 0
private const val PAGE_BALANCE = 1

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GroupsDetailsContent(
    modifier: Modifier = Modifier,
    group: GroupUiModel,
    onNewPaymentClick: (PaymentType) -> Unit = {},
    onEditClick: () -> Unit = {},
    onShareBalance: (String) -> Unit = {},
    onDeletePayment: (String, String) -> Unit = { _, _ -> },
    onNavigateBack: () -> Unit = {},
) {
    val listState = rememberLazyListState()
    val pages = listOf(
        ImageVector.vectorResource(R.drawable.ic_receipt) to stringResource(R.string.label_group_payments),
        ImageVector.vectorResource(R.drawable.ic_balance) to stringResource(R.string.label_group_balance),
    )
    var selectedPageIndex by rememberSaveable { mutableIntStateOf(0) }
    val pagerState = rememberPagerState(
        initialPage = PAGE_PAYMENTS,
        initialPageOffsetFraction = 0f
    ) { pages.size }
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier.testTag(TestTags.GroupDetailContent),
        topBar = {
            AppTopBar(
                title = group.title,
                navigationBack = onNavigateBack,
                actions = {
                    if (pagerState.currentPage == PAGE_BALANCE) {
                        IconButton(onClick = { onShareBalance(group.getBalanceForShare()) }) {
                            Icon(Icons.Filled.Share, stringResource(R.string.label_share_balance))
                        }
                    }
                    IconButton(onClick = onEditClick) {
                        Icon(Icons.Filled.Edit, stringResource(R.string.label_group_manage))
                    }
                },
            )
        },
        floatingActionButton = {
            GroupDetailsFloatingButtons(
                group = group,
                listState = listState,
                onNewPaymentClick = onNewPaymentClick,
                onNewMemberClick = onEditClick
            )
        },
        floatingActionButtonPosition = FabPosition.End,
    ) { contentPadding ->
        Box(
            modifier = Modifier.padding(contentPadding)
        ) {
            if (group.payments.isEmpty()) {
                if (group.members.size == 1) {
                    EmptyContent(
                        image = painterResource(R.drawable.add_member_img),
                        message = stringResource(R.string.message_add_members),
                    )
                } else {
                    EmptyContent(
                        image = painterResource(R.drawable.group_details_empty_img),
                        message = stringResource(R.string.message_no_expenses),
                    )
                }
            } else {
                Column(
                    modifier = Modifier.padding(horizontal = AppTheme.dimens.space8),
                ) {
                    SegmentedControl(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        selectedIndex = selectedPageIndex,
                        items = pages.map { it.second },
                        useFixedWidth = true,
                        itemWidth = 140.dp,
                        onItemSelection = { index ->
                            selectedPageIndex = index
                            scope.launch { pagerState.scrollToPage(index) }
                        }
                    )
                    VerticalSpacerSmall()
                    HorizontalPager(
                        state = pagerState,
                        userScrollEnabled = false
                    ) { page ->
                        when (page) {
                            PAGE_PAYMENTS -> GroupPayments(
                                group = group,
                                listState = listState,
                                onDeletePayment = onDeletePayment,
                            )
                            PAGE_BALANCE -> GroupBalance(group = group)
                        }
                    }
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(locale = "pt")
@Preview
@Composable
fun PreviewGroupDetails() {
    SharedBillTheme {
        Scaffold {
            GroupsDetailsContent(
                group = GroupUiModel.sample()
            ) {}
        }
    }
}
