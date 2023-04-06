plugins {
    id("sharedbill.android.feature")
    id("sharedbill.android.firebase")
}

android {
    namespace = "br.com.jwar.sharedbill.groups.presentation"
}

dependencies {
    implementation(project(":core:utility"))
    implementation(project(":feature:account:domain"))
    implementation(project(":feature:groups:domain"))
}