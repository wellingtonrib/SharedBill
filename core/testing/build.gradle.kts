plugins {
    id("sharedbill.android.library")
}

android {
    namespace = "br.com.jwar.sharedbill.core.testing"
}

dependencies {
    api(libs.bundles.test)

    implementation(project(":feature:account:domain"))
}