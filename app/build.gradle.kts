import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    id("sharedbill.android.application")
    id("com.google.gms.google-services")
}

android {
    signingConfigs {
        create("release") {
            val properties = Properties().apply {
                load(File("app/signing.properties").reader())
            }
            storeFile = File(properties.getProperty("storeFile"))
            storePassword = properties.getProperty("storePassword")
            keyPassword = properties.getProperty("keyPassword")
            keyAlias = properties.getProperty("keyAlias")
        }
    }
    defaultConfig {
        applicationId = "br.com.jwar.sharedbill"
        versionCode = 10
        versionName = "1.0.2"
        testInstrumentationRunner = "br.com.jwar.sharedbill.testing.HiltTestRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")
        }
    }

    namespace = "br.com.jwar.sharedbill"
}

dependencies {
    implementation(projects.core.designsystem)
    implementation(projects.core.utility)
    implementation(projects.core.common)
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
    implementation(libs.splashscreen)
    implementation(libs.hilt.android)
    implementation(libs.google.playServices.auth)

    testImplementation(libs.bundles.test)

    androidTestImplementation(libs.bundles.androidTest)
    androidTestImplementation(projects.core.testing)

    kapt(libs.hilt.android.compiler)
    kaptAndroidTest(libs.hilt.android.compiler)

    debugImplementation(libs.bundles.debug)
}