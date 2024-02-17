plugins {
    id("sharedbill.android.library")
    id("sharedbill.android.firebase")
    id("sharedbill.android.hilt")
}

android {
    defaultConfig {
        buildConfigField("String", "FIREBASE_WEB_CLIENT_ID", "\"${project.property("FIREBASE_WEB_CLIENT_ID").toString()}\"")
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