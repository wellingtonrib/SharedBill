package br.com.jwar.sharedbill.presentation.ui.theme

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Modifier.fillMaxWidthPaddingMedium() =
    Modifier.fillMaxWidth().then(paddingMedium())

@Composable
fun Modifier.paddingSmall() =
    Modifier.padding(AppTheme.dimens.space_4)

@Composable
fun Modifier.paddingMedium() =
    Modifier.padding(AppTheme.dimens.space_8)

@Composable
fun Modifier.paddingLarge() =
    Modifier.padding(AppTheme.dimens.space_10)

@Composable
fun Modifier.verticalPaddingMedium() =
    Modifier.padding(top = AppTheme.dimens.space_8, bottom = AppTheme.dimens.space_8)

@Composable
fun Modifier.horizontalSpaceSmall() =
    Modifier.width(AppTheme.dimens.space_6)

@Composable
fun Modifier.verticalSpaceSmall() =
    Modifier.height(AppTheme.dimens.space_6)

@Composable
fun Modifier.horizontalSpaceMedium() =
    Modifier.width(AppTheme.dimens.space_8)

@Composable
fun Modifier.verticalSpaceMedium() =
    Modifier.height(AppTheme.dimens.space_8)

@Composable
fun Modifier.sizeSmall() =
    Modifier.size(AppTheme.dimens.size_1)

@Composable
fun Modifier.sizeMedium() =
    Modifier.size(AppTheme.dimens.size_2)

@Composable
fun Modifier.sizeLarge() =
    Modifier.size(AppTheme.dimens.size_3)