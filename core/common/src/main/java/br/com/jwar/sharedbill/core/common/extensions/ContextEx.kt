package br.com.jwar.sharedbill.core.common.extensions

import android.content.Context
import br.com.jwar.sharedbill.core.common.R
import br.com.jwar.sharedbill.core.utility.extensions.openUrl
import br.com.jwar.sharedbill.core.utility.extensions.sendEmail

fun Context.launchSupportIntent() = this.sendEmail(
    addresses = arrayOf(this.getString(R.string.app_contact_mail)),
    subject = this.getString(R.string.app_contact_subject)
)

fun Context.launchTermsIntent() =
    this.openUrl(this.getString(R.string.app_terms_link))

fun Context.launchPrivacyIntent() =
    this.openUrl(this.getString(R.string.app_privacy_link))

fun Context.launchAboutIntent() =
    this.openUrl(this.getString(R.string.app_about_link))