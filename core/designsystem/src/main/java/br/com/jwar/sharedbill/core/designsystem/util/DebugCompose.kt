package br.com.jwar.sharedbill.core.designsystem.util

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import br.com.jwar.sharedbill.core.designsystem.BuildConfig
import kotlin.random.Random

@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.highlightComposition() =
    composed {
        if (BuildConfig.DEBUG) {
            Modifier.border(2.dp, getRandomColor())
        } else {
            this
        }
    }

fun getRandomColor() =  Color(
    red = Random.nextInt(256),
    green = Random.nextInt(256),
    blue = Random.nextInt(256),
    alpha = 255
)