package br.com.jwar.sharedbill.buildlogic.conventionplugin.plugins

import br.com.jwar.sharedbill.buildlogic.conventionplugin.extensions.configureAndroid
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
                apply("sharedbill.android.detekt")
                apply("sharedbill.android.kover")
            }

            extensions.configure<LibraryExtension> {
                defaultConfig.targetSdk = libs.findVersion("targetSdk").get().requiredVersion.toInt()
                configureAndroid(this)
            }
        }
    }
}