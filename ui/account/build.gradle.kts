plugins {
    id("sharedbill.android.feature")
}

android {
    namespace = "br.com.jwar.sharedbill.ui.account"
}

dependencies {
    implementation(libs.google.playServices.auth)

    implementation(project(":domain:account"))
}