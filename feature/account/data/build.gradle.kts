import java.util.*

plugins {
    id("sharedbill.android.library")
    id("sharedbill.android.firebase")
    id("sharedbill.android.hilt")
}

android {
    defaultConfig {
        val properties = Properties()
        properties.load(project.rootProject.file("local.properties").reader())

        buildConfigField("String", "FIREBASE_WEB_CLIENT_ID", "\"${properties.getProperty("FIREBASE_WEB_CLIENT_ID")}\"")
    }

    buildFeatures {
        buildConfig = true
    }

    namespace = "br.com.jwar.sharedbill.account.data"
}

dependencies {
    implementation(libs.google.playServices.auth)

    implementation(project(":feature:account:domain"))
}