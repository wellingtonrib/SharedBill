
import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
    id("kotlin-parcelize")
    id("kotlin-kapt")
}

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "br.com.jwar.sharedbill"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        val properties = Properties()
        properties.load(FileInputStream(File(rootProject.rootDir, "local.properties")))

        buildConfigField("String", "FIREBASE_WEB_CLIENT_ID", "\"${properties.getProperty("FIREBASE_WEB_CLIENT_ID")}\"")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        allWarningsAsErrors = false
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
            "-opt-in=androidx.compose.material.ExperimentalMaterialApi",
            "-opt-in=com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi",
            "-opt-in=androidx.compose.animation.ExperimentalAnimationApi",
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-opt-in=androidx.compose.ui.ExperimentalComposeUiApi"
        )
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.0"

    }
    packagingOptions {
        resources.excludes.add("/META-INF/{AL2.0,LGPL2.1}")
    }
    namespace = "br.com.jwar.sharedbill"
}

dependencies {

    val kotlin_version = "1.9.0"
    val coroutines_version = "1.6.4"
    val play_services_version = "1.6.2"
    val play_services_auth_version = "20.3.0"
    val firebase_bom_version = "31.0.2"
    val livedata_version = "2.6.0-alpha02"
    val compose_version = "1.3.0-rc01"
    val compose_material3 = "1.0.0-rc01"
    val compose_material = "1.3.0-rc01"
    val compose_navigaton = "2.6.0-alpha02"
    val accompanist_version = "0.26.0-alpha"
    val hilt_version = "2.43.2"
    val hilt_navigation_compose_version = "1.0.0"
    val coil_version = "2.2.2"
    val mockk_version = "1.12.7"
    val junit_version = "4.13.2"
    val ext_junit_version = "1.1.3"
    val expresso_version = "3.4.0"
    val core_test_version = "2.1.0"

    //AndroidX
    implementation("androidx.core:core-ktx:$kotlin_version")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$livedata_version")

    //Compose
    implementation("androidx.compose.ui:ui:$compose_version")
    implementation("androidx.compose.material3:material3:$compose_material3")
    implementation("androidx.compose.material:material:$compose_material")
    implementation("androidx.compose.ui:ui-tooling-preview:$compose_version")
    implementation("androidx.navigation:navigation-compose:$compose_navigaton")
    implementation("androidx.hilt:hilt-navigation-compose:$hilt_navigation_compose_version")
    implementation("io.coil-kt:coil-compose:$coil_version")

    //Accompanist
    implementation("com.google.accompanist:accompanist-navigation-material:$accompanist_version")
    implementation("com.google.accompanist:accompanist-navigation-animation:$accompanist_version")
    implementation("com.google.accompanist:accompanist-swiperefresh:$accompanist_version")

    //Hilt
    implementation("com.google.dagger:hilt-android:$hilt_version")
    kapt("com.google.dagger:hilt-android-compiler:$hilt_version")

    //Firebase
    implementation(platform("com.google.firebase:firebase-bom:$firebase_bom_version"))
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")

    //Play Services
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:$play_services_version")
    implementation("com.google.android.gms:play-services-auth:$play_services_auth_version")

    //Tests
    testImplementation("junit:junit:$junit_version")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutines_version")
    testImplementation("io.mockk:mockk:$mockk_version")
    testImplementation("androidx.arch.core:core-testing:$core_test_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    androidTestImplementation("androidx.test.ext:junit:$ext_junit_version")
    androidTestImplementation("androidx.test.espresso:espresso-core:$expresso_version")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$compose_version")

    //Debug
    debugImplementation("androidx.compose.ui:ui-tooling:$compose_version")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$compose_version")
}