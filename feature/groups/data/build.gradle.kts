plugins {
    id("sharedbill.android.library")
    id("sharedbill.android.firebase")
    id("sharedbill.android.hilt")
}

android {
    namespace = "br.com.jwar.sharedbill.groups.data"
}

dependencies {
    implementation(project(":core:utility"))
    implementation(project(":feature:account:domain"))
    implementation(project(":feature:groups:domain"))
}