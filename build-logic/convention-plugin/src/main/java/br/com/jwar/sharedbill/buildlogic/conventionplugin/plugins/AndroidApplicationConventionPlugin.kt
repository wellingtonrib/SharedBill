package br.com.jwar.sharedbill.buildlogic.conventionplugin.plugins

import br.com.jwar.sharedbill.buildlogic.conventionplugin.extensions.configureAndroid
import br.com.jwar.sharedbill.buildlogic.conventionplugin.extensions.configureCompose
import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType

class AndroidApplicationConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("dagger.hilt.android.plugin")
                apply("kotlin-parcelize")
                apply("kotlin-kapt")
                apply("com.google.gms.google-services")
                apply("com.google.firebase.crashlytics")
                apply("sharedbill.android.detekt")
            }

            extensions.configure<ApplicationExtension> {
                defaultConfig.targetSdk = libs.findVersion("targetSdk").get().requiredVersion.toInt()
                configureAndroid(this)
                configureCompose(this)
            }
        }
    }
}