plugins {
    id("sharedbill.android.library")
    id("sharedbill.android.firebase")
    id("sharedbill.android.hilt")
}

android {
    namespace = "br.com.jwar.sharedbill.groups.domain"
}

dependencies {
    implementation(projects.core.utility)
    implementation(projects.feature.account.domain)
}