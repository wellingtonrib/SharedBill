package br.com.jwar.sharedbill.presentation.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class Dimensions(
    val space_1: Dp,
    val space_2: Dp,
    val space_3: Dp,
    val space_4: Dp,
    val space_5: Dp,
    val space_6: Dp,
    val space_7: Dp,
    val space_8: Dp,
    val space_9: Dp,
    val space_10: Dp,
    val size_1: Dp,
    val size_2: Dp,
    val size_3: Dp
)

val DefaultDimens  = Dimensions(
    space_1 = 2.dp,
    space_2 = 4.dp,
    space_3 = 6.dp,
    space_4 = 8.dp,
    space_5 = 10.dp,
    space_6 = 12.dp,
    space_7 = 14.dp,
    space_8 = 16.dp,
    space_9 = 32.dp,
    space_10 = 42.dp,
    size_1 = 20.dp,
    size_2 = 40.dp,
    size_3 = 80.dp
)

val SmallDimens = Dimensions(
    space_1 = 1.dp,
    space_2 = 2.dp,
    space_3 = 3.dp,
    space_4 = 6.dp,
    space_5 = 8.dp,
    space_6 = 10.dp,
    space_7 = 12.dp,
    space_8 = 14.dp,
    space_9 = 28.dp,
    space_10 = 36.dp,
    size_1 = 10.dp,
    size_2 = 20.dp,
    size_3 = 60.dp
)

@Composable
fun ProvideDimens(dimens: Dimensions, content: @Composable () -> Unit) {
    val dimensionSet = remember { dimens }
    CompositionLocalProvider(LocalAppDimens provides dimensionSet, content = content)
}

val LocalAppDimens = staticCompositionLocalOf { DefaultDimens }