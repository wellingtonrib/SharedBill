plugins {
    `kotlin-dsl`
}

group = "br.com.jwar.sharedbill.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "sharedbill.android.application"
            implementationClass = "br.com.jwar.convention.plugins.AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "sharedbill.android.library"
            implementationClass = "br.com.jwar.convention.plugins.AndroidLibraryConventionPlugin"
        }
        register("androidFeature") {
            id = "sharedbill.android.feature"
            implementationClass = "br.com.jwar.convention.plugins.AndroidFeatureConventionPlugin"
        }
        register("androidFirebase") {
            id = "sharedbill.android.firebase"
            implementationClass = "br.com.jwar.convention.plugins.AndroidFirebaseConventionPlugin"
        }
        register("androidCompose") {
            id = "sharedbill.android.compose"
            implementationClass = "br.com.jwar.convention.plugins.AndroidComposeConventionPlugin"
        }
        register("androidHilt") {
            id = "sharedbill.android.hilt"
            implementationClass = "br.com.jwar.convention.plugins.AndroidHiltConventionPlugin"
        }
    }
}