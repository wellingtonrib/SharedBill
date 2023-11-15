package br.com.jwar.sharedbill.buildlogic.conventionplugin.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.project

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            with(pluginManager) {
                apply("sharedbill.android.library")
                apply("sharedbill.android.hilt")
                apply("sharedbill.android.compose")
            }

            dependencies {
                "implementation"(project(":core:designsystem"))
                "implementation"(project(":core:common"))
                "androidTestImplementation"(platform(libs.findLibrary("androidx-compose-bom").get()))
                "androidTestImplementation"(libs.findBundle("androidTest").get())
                "debugImplementation"(libs.findBundle("debug").get())
            }
        }
    }
}