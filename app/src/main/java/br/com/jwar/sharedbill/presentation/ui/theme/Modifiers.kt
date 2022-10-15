package br.com.jwar.sharedbill.presentation.ui.theme

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Modifier.fillMaxWidthPaddingMedium() =
    Modifier.fillMaxWidth().then(paddingMedium())

@Composable
fun Modifier.paddingMedium() =
    Modifier.padding(AppTheme.dimens.space_8)

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