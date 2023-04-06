plugins {
    id("sharedbill.android.library")
    id("sharedbill.android.firebase")
}

android {
    namespace = "br.com.jwar.sharedbill.core.utility"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.coroutines.playServices)
}