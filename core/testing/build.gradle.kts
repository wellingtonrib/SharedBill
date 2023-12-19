plugins {
    id("sharedbill.android.library")
}

android {
    namespace = "br.com.jwar.sharedbill.core.testing"
}

dependencies {
    api(libs.bundles.test)
    api(platform(libs.androidx.compose.bom))
    api(libs.bundles.androidTest)

    implementation(projects.feature.account.domain)
    implementation(projects.feature.groups.domain)
}