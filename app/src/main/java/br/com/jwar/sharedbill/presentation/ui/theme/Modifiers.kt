package br.com.jwar.sharedbill.presentation.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Modifier.fillMaxWidthPaddingMedium() =
    Modifier
        .fillMaxWidth()
        .then(paddingMedium())

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

@Composable
fun Modifier.verticalSpaceSmall() =
    Modifier.height(AppTheme.dimens.space_6)

@Composable
fun Modifier.verticalSpaceMedium() =
    Modifier.height(AppTheme.dimens.space_8)

@Composable
fun Modifier.verticalSpaceLarge() =
    Modifier.height(AppTheme.dimens.space_10)

@Composable
fun Modifier.horizontalSpaceSmall() =
    Modifier.width(AppTheme.dimens.space_6)

@Composable
fun Modifier.horizontalSpaceMedium() =
    Modifier.width(AppTheme.dimens.space_8)

@Composable
fun Modifier.horizontalSpaceLarge() =
    Modifier.width(AppTheme.dimens.space_10)

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
fun Modifier.verticalPaddingSmall() =
    Modifier.padding(top = AppTheme.dimens.space_4, bottom = AppTheme.dimens.space_4)

@Composable
fun Modifier.verticalPaddingMedium() =
    Modifier.padding(top = AppTheme.dimens.space_8, bottom = AppTheme.dimens.space_8)

@Composable
fun Modifier.verticalPaddingLarge() =
    Modifier.padding(top = AppTheme.dimens.space_10, bottom = AppTheme.dimens.space_10)

@Composable
fun Modifier.horizontalPaddingSmall() =
    Modifier.padding(start = AppTheme.dimens.space_4, end = AppTheme.dimens.space_4)

@Composable
fun Modifier.horizontalPaddingMedium() =
    Modifier.padding(start = AppTheme.dimens.space_8, end = AppTheme.dimens.space_8)

@Composable
fun Modifier.horizontalPaddingLarge() =
    Modifier.padding(start = AppTheme.dimens.space_10, end = AppTheme.dimens.space_10)

@Composable
fun Modifier.sizeSmall() =
    Modifier.size(AppTheme.dimens.size_1)

@Composable
fun Modifier.sizeMedium() =
    Modifier.size(AppTheme.dimens.size_2)

@Composable
fun Modifier.sizeLarge() =
    Modifier.size(AppTheme.dimens.size_3)