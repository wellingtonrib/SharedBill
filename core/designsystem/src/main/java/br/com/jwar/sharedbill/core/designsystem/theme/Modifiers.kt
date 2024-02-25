package br.com.jwar.sharedbill.core.designsystem.theme

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
    then(height(AppTheme.dimens.space_6))

@Composable
fun Modifier.verticalSpaceMedium() =
    then(height(AppTheme.dimens.space_8))

@Composable
fun Modifier.verticalSpaceLarge() =
    then(height(AppTheme.dimens.space_10))

@Composable
fun Modifier.horizontalSpaceSmall() =
    then(width(AppTheme.dimens.space_6))

@Composable
fun Modifier.horizontalSpaceMedium() =
    then(width(AppTheme.dimens.space_8))

@Composable
fun Modifier.horizontalSpaceLarge() =
    then(width(AppTheme.dimens.space_10))

@Composable
fun Modifier.paddingSmall() =
    then(padding(AppTheme.dimens.space_4))

@Composable
fun Modifier.paddingMedium() =
    then(padding(AppTheme.dimens.space_8))

@Composable
fun Modifier.paddingLarge() =
    then(padding(AppTheme.dimens.space_10))

@Composable
fun Modifier.verticalPaddingSmall() =
    then(padding(top = AppTheme.dimens.space_4, bottom = AppTheme.dimens.space_4))

@Composable
fun Modifier.verticalPaddingMedium() =
    then(padding(top = AppTheme.dimens.space_8, bottom = AppTheme.dimens.space_8))

@Composable
fun Modifier.verticalPaddingLarge() =
    then(padding(top = AppTheme.dimens.space_10, bottom = AppTheme.dimens.space_10))

@Composable
fun Modifier.horizontalPaddingSmall() =
    then(padding(start = AppTheme.dimens.space_4, end = AppTheme.dimens.space_4))

@Composable
fun Modifier.horizontalPaddingMedium() =
    then(padding(start = AppTheme.dimens.space_8, end = AppTheme.dimens.space_8))

@Composable
fun Modifier.horizontalPaddingLarge() =
    then(padding(start = AppTheme.dimens.space_10, end = AppTheme.dimens.space_10))

@Composable
fun Modifier.sizeSmall() =
    then(size(AppTheme.dimens.size_1))

@Composable
fun Modifier.sizeMedium() =
    then(size(AppTheme.dimens.size_2))

@Composable
fun Modifier.sizeLarge() =
    then(size(AppTheme.dimens.size_3))