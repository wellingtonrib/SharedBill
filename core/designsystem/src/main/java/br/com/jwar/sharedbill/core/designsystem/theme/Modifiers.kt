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
    composed { Modifier
        .fillMaxWidth()
        .then(paddingMedium()) }

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
    composed { composed { then(height(AppTheme.dimens.space_6)) } }

fun Modifier.verticalSpaceMedium() =
    composed { then(height(AppTheme.dimens.space_8)) }

fun Modifier.verticalSpaceLarge() =
    composed { then(height(AppTheme.dimens.space_10)) }

fun Modifier.horizontalSpaceSmall() =
    composed { then(width(AppTheme.dimens.space_6)) }

fun Modifier.horizontalSpaceMedium() =
    composed { then(width(AppTheme.dimens.space_8)) }

fun Modifier.horizontalSpaceLarge() =
    composed { then(width(AppTheme.dimens.space_10)) }

fun Modifier.paddingSmall() =
    composed { composed { then(padding(AppTheme.dimens.space_4)) } }

fun Modifier.paddingMedium() =
    composed { then(padding(AppTheme.dimens.space_8)) }

fun Modifier.paddingLarge() =
    composed { then(padding(AppTheme.dimens.space_10)) }

fun Modifier.verticalPaddingSmall() =
    composed { then(padding(top = AppTheme.dimens.space_4, bottom = AppTheme.dimens.space_4)) }

fun Modifier.verticalPaddingMedium() =
    composed { composed { then(padding(top = AppTheme.dimens.space_8, bottom = AppTheme.dimens.space_8)) } }

fun Modifier.verticalPaddingLarge() =
    composed { then(padding(top = AppTheme.dimens.space_10, bottom = AppTheme.dimens.space_10)) }

fun Modifier.horizontalPaddingSmall() =
    composed { then(padding(start = AppTheme.dimens.space_4, end = AppTheme.dimens.space_4)) }

fun Modifier.horizontalPaddingMedium() =
    composed { then(padding(start = AppTheme.dimens.space_8, end = AppTheme.dimens.space_8)) }

fun Modifier.horizontalPaddingLarge() =
    composed { then(padding(start = AppTheme.dimens.space_10, end = AppTheme.dimens.space_10)) }

fun Modifier.sizeSmall() =
    composed { then(size(AppTheme.dimens.size_1)) }

fun Modifier.sizeMedium() =
    composed { then(size(AppTheme.dimens.size_2)) }

fun Modifier.sizeLarge() =
    composed { then(size(AppTheme.dimens.size_3)) }
