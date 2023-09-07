package br.com.jwar.sharedbill.core.designsystem.util

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import br.com.jwar.sharedbill.core.designsystem.BuildConfig

class Ref(var value: Int)

@Composable
inline fun LogCompositions(msg: String) {
    if (BuildConfig.DEBUG) {
        val ref = remember { Ref(0) }
        SideEffect { ref.value++ }
        Log.d("COMPOSITION", "Compositions: $msg ${ref.value}")
    }
}