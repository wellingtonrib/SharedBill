plugins {
    id("sharedbill.android.library")
    id("sharedbill.android.firebase")
    id("sharedbill.android.hilt")
}

android {
    namespace = "br.com.jwar.sharedbill.domain.account"
}

dependencies {
    implementation(libs.google.playServices.auth)

    implementation(project(":core:utility"))
}

