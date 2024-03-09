package br.com.jwar.sharedbill.account.presentation.ui.auth.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.jwar.sharedbill.account.presentation.R
import br.com.jwar.sharedbill.core.designsystem.theme.AppTheme
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.core.designsystem.theme.VerticalSpacerMedium

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun ColumnScope.Onboarding(
    modifier: Modifier = Modifier,
) {
    val onboardingItems = listOf(
        OnBoardingItem(
            imageRes = R.drawable.img_onboarding_1,
            titleRes = R.string.onboarding_title_1,
            messageRes = R.string.onboarding_message_1,
        ),
        OnBoardingItem(
            imageRes = R.drawable.img_onboarding_2,
            titleRes = R.string.onboarding_title_2,
            messageRes = R.string.onboarding_message_2,
        ),
        OnBoardingItem(
            imageRes = R.drawable.img_onboarding_3,
            titleRes = R.string.onboarding_title_3,
            messageRes = R.string.onboarding_message_3,
        )
    )
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        onboardingItems.size
    }
    HorizontalPager(
        modifier = modifier,
        state = pagerState
    ) {
        with(onboardingItems[pagerState.currentPage]) {
            OnBoardingItemComponent(
                image = painterResource(imageRes),
                title = stringResource(titleRes),
                message = stringResource(messageRes),
            )
        }
    }
    VerticalSpacerMedium()
    IndicatorsComponent(size = onboardingItems.size, index = pagerState.currentPage)
}

@Composable
fun OnBoardingItemComponent(
    image: Painter,
    title: String,
    message: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = image,
            contentDescription = null,
            modifier = Modifier.padding(start = 50.dp, end = 50.dp)
        )
        VerticalSpacerMedium()
        Text(
            text = title,
            style = AppTheme.typo.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            letterSpacing = 1.sp,
        )
        Text(
            text = message,
            style = AppTheme.typo.bodyLarge,
            fontWeight = FontWeight.Light,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(10.dp),
            letterSpacing = 1.sp,
        )
    }
}

@Composable
fun ColumnScope.IndicatorsComponent(size: Int, index: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.align(Alignment.CenterHorizontally)
    ) {
        repeat(size) {
            Indicator(isSelected = it == index)
        }
    }
}

@Composable
fun Indicator(isSelected: Boolean) {
    val width = animateDpAsState(
        targetValue = if (isSelected) 25.dp else 10.dp,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )
    Box(
        modifier = Modifier
            .height(10.dp)
            .width(width.value)
            .clip(CircleShape)
            .background(
                color = if (isSelected) AppTheme.colors.primary else Color.Gray
            )
    ) {}
}

data class OnBoardingItem(
    val imageRes: Int,
    val titleRes: Int,
    val messageRes: Int,
)

@Preview
@Composable
fun PreviewOnboarding() {
    SharedBillTheme {
        Column {
            Onboarding()
        }
    }
}
