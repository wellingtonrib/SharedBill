plugins {
    id("sharedbill.android.feature")
    id("sharedbill.android.firebase")
}

android {
    namespace = "br.com.jwar.sharedbill.groups.presentation"
}

dependencies {
    implementation(projects.feature.account.domain)
    implementation(projects.feature.groups.domain)
}