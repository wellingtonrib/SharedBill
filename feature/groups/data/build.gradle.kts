plugins {
    id("sharedbill.android.library")
    id("sharedbill.android.firebase")
    id("sharedbill.android.hilt")
}

android {
    namespace = "br.com.jwar.sharedbill.groups.data"
}

dependencies {
    implementation(projects.core.utility)
    implementation(projects.feature.account.domain)
    implementation(projects.feature.groups.domain)
    testImplementation(projects.core.testing)
}