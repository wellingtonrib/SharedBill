plugins {
    id("sharedbill.android.library")
}

android {
    namespace = "br.com.jwar.sharedbill.core.utility"
}

dependencies {
    implementation(libs.kotlinx.coroutines.playServices)
}