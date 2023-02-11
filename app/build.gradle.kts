import java.util.*

plugins {
    id("sharedbill.android.application")
    id("com.google.gms.google-services")
}

android {
    defaultConfig {
        applicationId = "br.com.jwar.sharedbill"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        val properties = Properties()
        properties.load(project.rootProject.file("local.properties").reader())

        buildConfigField("String", "FIREBASE_WEB_CLIENT_ID", "\"${properties.getProperty("FIREBASE_WEB_CLIENT_ID")}\"")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    packagingOptions {
        resources.excludes.add("/META-INF/{AL2.0,LGPL2.1}")
    }

    namespace = "br.com.jwar.sharedbill"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.kotlinx.coroutines.playServices)
    implementation(libs.google.playServices.auth)
    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebase)
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.bundles.compose)
    testImplementation(libs.bundles.test)
    androidTestImplementation(libs.bundles.androidTest)
    debugImplementation(libs.bundles.debug)

    implementation(project(":core:designsystem"))
}