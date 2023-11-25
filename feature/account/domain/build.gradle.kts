plugins {
    id("sharedbill.android.library")
    id("sharedbill.android.firebase")
    id("sharedbill.android.hilt")
}

android {
    namespace = "br.com.jwar.sharedbill.account.domain"
}

dependencies {
    implementation(libs.google.playServices.auth)
    implementation(projects.core.utility)

    testImplementation(projects.core.testing)
}
