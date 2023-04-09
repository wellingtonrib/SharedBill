plugins {
    id("sharedbill.android.library")
}

android {
    namespace = "br.com.jwar.sharedbill.core.common"
}

dependencies {
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
}