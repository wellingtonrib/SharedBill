package br.com.jwar.sharedbill.core.utility

import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase

class FirebaseExceptionHandler : ExceptionHandler {
    override fun recordException(e: Exception) {
        Firebase.crashlytics.recordException(e)
    }
}
