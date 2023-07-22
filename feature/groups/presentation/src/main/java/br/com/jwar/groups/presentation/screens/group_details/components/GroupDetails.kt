package br.com.jwar.groups.presentation.screens.group_details.components

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.groups.presentation.models.GroupUiModel
import br.com.jwar.groups.presentation.models.PaymentType
import br.com.jwar.sharedbill.core.designsystem.components.BackNavigationIcon
import br.com.jwar.sharedbill.core.designsystem.components.EmptyContent
import br.com.jwar.sharedbill.core.designsystem.components.Title
import br.com.jwar.sharedbill.core.designsystem.theme.AppTheme
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.core.designsystem.theme.VerticalSpacerSmall
import br.com.jwar.sharedbill.groups.presentation.R
import kotlinx.coroutines.launch

private const val PAGE_PAYMENTS = 0
private const val PAGE_BALANCE = 1

typealias Page = Pair<ImageVector, String>

@Composable
fun GroupsDetails(
    group: GroupUiModel,
    onNewPaymentClick: (PaymentType)-> Unit = {},
    onEditClick: () -> Unit = {},
    onShareBalance: (String) -> Unit = {},
    onNavigateBack: () -> Unit = {},
) {
    val listState = rememberLazyListState()
    val topBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(topBarScrollBehavior.nestedScrollConnection),
        topBar = {
            GroupDetailsTopBar(
                group = group,
                topBarScrollBehavior = topBarScrollBehavior,
                onEditClick = onEditClick,
                onNavigateBack = onNavigateBack
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
        GroupDetailsContent(
            group = group,
            contentPadding = contentPadding,
            listState = listState,
            onShareBalance = onShareBalance,
        )
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun GroupDetailsContent(
    group: GroupUiModel,
    contentPadding: PaddingValues,
    listState: LazyListState,
    onShareBalance: (String) -> Unit = {},
) {
    val pages = listOf(
        Icons.Default.Menu to stringResource(R.string.label_group_payments),
        Icons.Default.CheckCircle to stringResource(R.string.label_group_balance),
    )
    val pagerState = rememberPagerState(
        initialPage = PAGE_PAYMENTS,
        initialPageOffsetFraction = 0f
    ) { pages.size }
    val scope = rememberCoroutineScope()

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
                modifier = Modifier.padding(horizontal = AppTheme.dimens.space_8)
            ) {
                TabBar(
                    pages = pages,
                    currentPage = pagerState.currentPage,
                    onPageSelected = { index -> scope.launch { pagerState.scrollToPage(index) } }
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

@Composable
private fun GroupDetailsFloatingButtons(
    listState: LazyListState,
    onNewPaymentClick: (PaymentType) -> Unit
) {
    var actionButtonsVisible by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.End
    ) {
        if (actionButtonsVisible) {
            ExtendedFloatingActionButton(
                onClick = { onNewPaymentClick(PaymentType.Expense) },
                expanded = true,
                icon = {
                    Icon(
                        Icons.Outlined.ShoppingCart,
                        stringResource(R.string.label_payment_new_expense)
                    )
                },
                text = { Text(stringResource(R.string.label_payment_new_expense)) },
            )
            VerticalSpacerSmall()
            ExtendedFloatingActionButton(
                onClick = { onNewPaymentClick(PaymentType.Settlement) },
                expanded = true,
                icon = {
                    Icon(
                        Icons.Outlined.ThumbUp,
                        stringResource(R.string.label_payment_new_settlement)
                    )
                },
                text = { Text(stringResource(R.string.label_payment_new_settlement)) },
            )
            VerticalSpacerSmall()
        }
        ExtendedFloatingActionButton(
            onClick = { actionButtonsVisible = !actionButtonsVisible },
            expanded = listState.isScrollingUp() && !actionButtonsVisible,
            icon = { Icon(Icons.Filled.Add, stringResource(R.string.label_payment_new)) },
            text = { Text(stringResource(R.string.label_payment_new)) },
        )
    }
}

@Composable
private fun GroupDetailsTopBar(
    group: GroupUiModel,
    topBarScrollBehavior: TopAppBarScrollBehavior,
    onEditClick: () -> Unit,
    onNavigateBack: () -> Unit
) {
    MediumTopAppBar(
        navigationIcon = { BackNavigationIcon(onNavigateBack) },
        title = { Title(group.title) },
        actions = {
            IconButton(onClick = onEditClick) {
                Icon(Icons.Filled.Edit, stringResource(id = R.string.label_group_manage))
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            scrolledContainerColor = Color.Unspecified,
            containerColor = Color.Unspecified
        ),
        scrollBehavior = topBarScrollBehavior
    )
}

@Composable
fun TabBar(
    pages: List<Page>,
    currentPage: Int,
    onPageSelected: (Int) -> Unit
) {
    TabRow(
        selectedTabIndex = currentPage,
    ) {
        pages.forEachIndexed { index, (icon, text) ->
            Tab(
                selected = currentPage == index,
                onClick = { onPageSelected(index) },
                icon = { Icon(imageVector = icon, contentDescription = text) },
                text = { Text(text = text) }
            )
        }
    }
}

@Composable
private fun LazyListState.isScrollingUp() : Boolean {
    var previousIndex by remember(this) {
        mutableIntStateOf(firstVisibleItemIndex)
    }
    var previousScrollOffset by remember(this) {
        mutableIntStateOf(firstVisibleItemScrollOffset)
    }
    return remember(this) {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex > firstVisibleItemIndex
            } else {
                previousScrollOffset >= firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }.value
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun PreviewGroupDetails() {
    SharedBillTheme {
        Scaffold {
            GroupsDetails(GroupUiModel.sample())
        }
    }
}
