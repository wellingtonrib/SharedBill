package br.com.jwar.sharedbill.core.designsystem.util

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class UiText {
    data class DynamicString(val value: String): UiText()
    class StringResource(@StringRes val resId: Int, vararg var args: Any): UiText()

    @Composable
    fun asString() = when(this) {
        is DynamicString -> value
        is StringResource -> stringResource(resId, *args)
    }

    fun asString(context: Context) = when(this) {
        is DynamicString -> value
        is StringResource -> context.getString(resId, *args)
    }

    @Composable
    fun asText() = Text(text = this.asString())
}