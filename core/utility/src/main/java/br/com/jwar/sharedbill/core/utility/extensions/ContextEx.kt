package br.com.jwar.sharedbill.core.utility.extensions

import android.content.Context
import android.content.Intent

fun Context.shareText(text: String) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/plain"
    intent.putExtra(Intent.EXTRA_TEXT, text)
    this.startActivity(Intent.createChooser(intent, "Share with"))
}