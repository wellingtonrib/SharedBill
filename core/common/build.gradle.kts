plugins {
    id("sharedbill.android.library")
}

android {
    namespace = "br.com.jwar.sharedbill.core.common"
}

dependencies {
    implementation(projects.core.utility)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
}