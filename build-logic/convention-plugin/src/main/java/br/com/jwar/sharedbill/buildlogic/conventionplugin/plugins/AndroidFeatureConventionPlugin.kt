package br.com.jwar.sharedbill.buildlogic.conventionplugin.plugins

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
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

            extensions.configure<LibraryExtension> {
                defaultConfig {
                    testInstrumentationRunner = "br.com.jwar.sharedbill.testing.HiltTestRunner"
                }
            }

            dependencies {
                "implementation"(project(":core:designsystem"))
                "implementation"(project(":core:common"))
                "implementation"(project(":core:utility"))
                "implementation"(project(":core:testing"))

                "debugImplementation"(libs.findBundle("debug").get())
            }
        }
    }
}