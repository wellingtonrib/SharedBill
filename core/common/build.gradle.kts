plugins {
    id("sharedbill.android.library")
}

android {
    namespace = "br.com.jwar.sharedbill.core.common"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.kotlinx.coroutines.playServices)
}