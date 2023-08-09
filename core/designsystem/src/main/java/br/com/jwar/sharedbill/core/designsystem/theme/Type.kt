package br.com.jwar.sharedbill.core.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import br.com.jwar.sharedbill.core.designsystem.R

private val defaultTypography = Typography()

object AppFont {
    val Nunito = FontFamily(
        Font(R.font.nunito_sans),
        Font(R.font.nunito_sans_italic, style = FontStyle.Italic),
        Font(R.font.nunito_sans_semibold, FontWeight.Medium),
        Font(R.font.nunito_sans_semibold_italic, FontWeight.Medium, style = FontStyle.Italic),
        Font(R.font.nunito_sans_bold, FontWeight.Bold),
        Font(R.font.nunito_sans_bold_italic, FontWeight.Bold, style = FontStyle.Italic)
    )
}

val DefaultTypography = Typography(
    displayLarge = defaultTypography.displayLarge.copy(fontFamily = AppFont.Nunito),
    displayMedium = defaultTypography.displayMedium.copy(fontFamily = AppFont.Nunito),
    displaySmall = defaultTypography.displaySmall.copy(fontFamily = AppFont.Nunito),

    headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = AppFont.Nunito),
    headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = AppFont.Nunito),
    headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = AppFont.Nunito),

    titleLarge = defaultTypography.titleLarge.copy(fontFamily = AppFont.Nunito),
    titleMedium = defaultTypography.titleMedium.copy(fontFamily = AppFont.Nunito),
    titleSmall = defaultTypography.titleSmall.copy(fontFamily = AppFont.Nunito),

    bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = AppFont.Nunito),
    bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = AppFont.Nunito),
    bodySmall = defaultTypography.bodySmall.copy(fontFamily = AppFont.Nunito),

    labelLarge = defaultTypography.labelLarge.copy(fontFamily = AppFont.Nunito),
    labelMedium = defaultTypography.labelMedium.copy(fontFamily = AppFont.Nunito),
    labelSmall = defaultTypography.labelSmall.copy(fontFamily = AppFont.Nunito)
)

val LocalAppTypo = staticCompositionLocalOf { DefaultTypography }