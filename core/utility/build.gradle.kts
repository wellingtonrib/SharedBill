plugins {
    id("sharedbill.android.library")
    id("sharedbill.android.compose")
}

android {
    namespace = "br.com.jwar.sharedbill.core.utility"
}

dependencies {
    implementation(libs.kotlinx.coroutines.playServices)
}