package br.com.jwar.sharedbill.buildlogic.conventionplugin.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidHiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            with(pluginManager) {
                apply("dagger.hilt.android.plugin")
                apply("kotlin-kapt")
            }

            dependencies {
                "implementation"(libs.findLibrary("hilt.android").get())
                "kapt"(libs.findLibrary("hilt.android.compiler").get())
                "kaptAndroidTest"(libs.findLibrary("hilt.android.compiler").get())
            }
        }
    }
}