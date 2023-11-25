plugins {
    id("sharedbill.android.feature")
}

android {
    namespace = "br.com.jwar.sharedbill.account.presentation"
}

dependencies {
    implementation(libs.google.playServices.auth)
    implementation(projects.feature.account.domain)
}