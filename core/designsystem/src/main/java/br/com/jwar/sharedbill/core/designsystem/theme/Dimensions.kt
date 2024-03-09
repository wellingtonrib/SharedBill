package br.com.jwar.sharedbill.core.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Suppress("LongParameterList", "unused")
class Dimensions(
    val space1: Dp,
    val space2: Dp,
    val space3: Dp,
    val space4: Dp,
    val space5: Dp,
    val space6: Dp,
    val space7: Dp,
    val space8: Dp,
    val space9: Dp,
    val space10: Dp,
    val size1: Dp,
    val size2: Dp,
    val size3: Dp,
    val size4: Dp,
    val size5: Dp,
)

val DefaultDimens = Dimensions(
    space1 = 2.dp,
    space2 = 4.dp,
    space3 = 6.dp,
    space4 = 8.dp,
    space5 = 10.dp,
    space6 = 12.dp,
    space7 = 14.dp,
    space8 = 16.dp,
    space9 = 32.dp,
    space10 = 42.dp,
    size1 = 20.dp,
    size2 = 40.dp,
    size3 = 80.dp,
    size4 = 100.dp,
    size5 = 120.dp,
)

val SmallDimens = Dimensions(
    space1 = 1.dp,
    space2 = 2.dp,
    space3 = 3.dp,
    space4 = 6.dp,
    space5 = 8.dp,
    space6 = 10.dp,
    space7 = 12.dp,
    space8 = 14.dp,
    space9 = 28.dp,
    space10 = 36.dp,
    size1 = 10.dp,
    size2 = 20.dp,
    size3 = 60.dp,
    size4 = 80.dp,
    size5 = 100.dp,
)

@Composable
fun ProvideDimens(dimens: Dimensions, content: @Composable () -> Unit) {
    val dimensionSet = remember { dimens }
    CompositionLocalProvider(LocalAppDimens provides dimensionSet, content = content)
}

val LocalAppDimens = staticCompositionLocalOf { DefaultDimens }
