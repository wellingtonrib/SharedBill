plugins {
    id("sharedbill.android.application")
    id("com.google.gms.google-services")
}

android {
    defaultConfig {
        applicationId = "br.com.jwar.sharedbill"
        versionCode = 4
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    packagingOptions {
        resources.excludes.add("META-INF/LICENSE.md")
        resources.excludes.add("META-INF/LICENSE-notice.md")
    }

    namespace = "br.com.jwar.sharedbill"
}

dependencies {
    implementation(projects.core.designsystem)
    implementation(projects.core.utility)
    implementation(projects.feature.account.domain)
    implementation(projects.feature.account.data)
    implementation(projects.feature.account.presentation)
    implementation(projects.feature.groups.domain)
    implementation(projects.feature.groups.data)
    implementation(projects.feature.groups.presentation)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.kotlinx.coroutines.playServices)
    implementation(libs.bundles.compose)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.appcheck.playintegrity)
    implementation(libs.firebase.appcheck.debug)
    implementation(libs.firebase.appcheck.ktx)
    implementation(libs.google.play.integrity)

    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    testImplementation(libs.bundles.test)
    androidTestImplementation(libs.bundles.androidTest)

    debugImplementation(libs.bundles.debug)
}