plugins {
    id("sharedbill.android.library")
    id("sharedbill.android.firebase")
    id("sharedbill.android.hilt")
}

android {
    defaultConfig {
        val firebaseWebClientId = project.findProperty("FIREBASE_WEB_CLIENT_ID")?.toString() ?: "FIREBASE_WEB_CLIENT_ID"
        buildConfigField("String", "FIREBASE_WEB_CLIENT_ID", "\"$firebaseWebClientId\"")
    }

    buildFeatures {
        buildConfig = true
    }

    namespace = "br.com.jwar.sharedbill.account.data"
}

dependencies {
    implementation(libs.google.playServices.auth)
    implementation(projects.feature.account.domain)

    testImplementation(projects.core.testing)
}