@file:Suppress("TooManyFunctions", "unused")

package br.com.jwar.sharedbill.core.designsystem.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.fillMaxWidthPaddingMedium() =
    composed {
        Modifier
            .fillMaxWidth()
            .then(paddingMedium())
    }

@Composable
fun VerticalSpacerSmall() = Spacer(modifier = Modifier.verticalSpaceSmall())

@Composable
fun VerticalSpacerMedium() = Spacer(modifier = Modifier.verticalSpaceMedium())

@Composable
fun VerticalSpacerLarge() = Spacer(modifier = Modifier.verticalSpaceLarge())

@Composable
fun HorizontalSpacerSmall() = Spacer(modifier = Modifier.horizontalSpaceSmall())

@Composable
fun HorizontalSpacerMedium() = Spacer(modifier = Modifier.horizontalSpaceMedium())

@Composable
fun HorizontalSpacerLarge() = Spacer(modifier = Modifier.horizontalSpaceLarge())

fun Modifier.verticalSpaceSmall() =
    composed { composed { then(height(AppTheme.dimens.space6)) } }

fun Modifier.verticalSpaceMedium() =
    composed { then(height(AppTheme.dimens.space8)) }

fun Modifier.verticalSpaceLarge() =
    composed { then(height(AppTheme.dimens.space10)) }

fun Modifier.horizontalSpaceSmall() =
    composed { then(width(AppTheme.dimens.space6)) }

fun Modifier.horizontalSpaceMedium() =
    composed { then(width(AppTheme.dimens.space8)) }

fun Modifier.horizontalSpaceLarge() =
    composed { then(width(AppTheme.dimens.space10)) }

fun Modifier.paddingSmall() =
    composed { composed { then(padding(AppTheme.dimens.space4)) } }

fun Modifier.paddingMedium() =
    composed { then(padding(AppTheme.dimens.space8)) }

fun Modifier.paddingLarge() =
    composed { then(padding(AppTheme.dimens.space10)) }

fun Modifier.verticalPaddingSmall() =
    composed { then(padding(top = AppTheme.dimens.space4, bottom = AppTheme.dimens.space4)) }

fun Modifier.verticalPaddingMedium() =
    composed { composed { then(padding(top = AppTheme.dimens.space8, bottom = AppTheme.dimens.space8)) } }

fun Modifier.verticalPaddingLarge() =
    composed { then(padding(top = AppTheme.dimens.space10, bottom = AppTheme.dimens.space10)) }

fun Modifier.horizontalPaddingSmall() =
    composed { then(padding(start = AppTheme.dimens.space4, end = AppTheme.dimens.space4)) }

fun Modifier.horizontalPaddingMedium() =
    composed { then(padding(start = AppTheme.dimens.space8, end = AppTheme.dimens.space8)) }

fun Modifier.horizontalPaddingLarge() =
    composed { then(padding(start = AppTheme.dimens.space10, end = AppTheme.dimens.space10)) }

fun Modifier.sizeSmall() =
    composed { then(size(AppTheme.dimens.size1)) }

fun Modifier.sizeMedium() =
    composed { then(size(AppTheme.dimens.size2)) }

fun Modifier.sizeLarge() =
    composed { then(size(AppTheme.dimens.size3)) }
