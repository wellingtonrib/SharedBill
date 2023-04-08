package br.com.jwar.sharedbill.buildlogic.conventionplugin.plugins

import br.com.jwar.sharedbill.buildlogic.conventionplugin.extensions.configureCompose
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            pluginManager.apply("com.android.library")

            extensions.configure<LibraryExtension> {
                configureCompose(this)
            }

            dependencies {
                "implementation"(libs.findBundle("compose").get())
            }
        }
    }
}