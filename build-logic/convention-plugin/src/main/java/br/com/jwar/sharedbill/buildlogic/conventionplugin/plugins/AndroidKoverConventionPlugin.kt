package br.com.jwar.sharedbill.buildlogic.conventionplugin.plugins

import br.com.jwar.sharedbill.buildlogic.conventionplugin.extensions.configureKover
import kotlinx.kover.gradle.plugin.dsl.KoverReportExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidKoverConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("org.jetbrains.kotlinx.kover")
            val extension = extensions.getByType<KoverReportExtension>()
            configureKover(extension)
        }
    }
}