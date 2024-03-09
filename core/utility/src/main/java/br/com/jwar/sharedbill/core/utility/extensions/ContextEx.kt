package br.com.jwar.sharedbill.core.utility.extensions

import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.shareText(text: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, text)
    }
    this.startActivity(Intent.createChooser(intent, "Share with"))
}

fun Context.sendEmail(addresses: Array<String>, subject: String) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:")
        putExtra(Intent.EXTRA_EMAIL, addresses)
        putExtra(Intent.EXTRA_SUBJECT, subject)
    }
    this.startActivity(intent)
}

fun Context.openUrl(url: String) {
    val intent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse(url)
    }
    this.startActivity(intent)
}
