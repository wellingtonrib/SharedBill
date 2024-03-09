package br.com.jwar.sharedbill

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.AppCheckProviderFactory
import com.google.firebase.appcheck.ktx.appCheck
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var appCheckProviderFactory: AppCheckProviderFactory

    override fun onCreate() {
        super.onCreate()

        initializeFirebase()
    }

    private fun initializeFirebase() {
        FirebaseApp.initializeApp(this)
        Firebase.appCheck.installAppCheckProviderFactory(appCheckProviderFactory)
    }
}
