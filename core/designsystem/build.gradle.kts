plugins {
    id("sharedbill.android.library")
    id("sharedbill.android.compose")
}

android {
    namespace = "br.com.jwar.sharedbill.core.designsystem"
}

dependencies {
    debugImplementation(libs.bundles.debug)
}