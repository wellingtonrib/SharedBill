package br.com.jwar.sharedbill.buildlogic.conventionplugin.plugins

import br.com.jwar.sharedbill.buildlogic.conventionplugin.extensions.configureDetekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidDetektConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("io.gitlab.arturbosch.detekt")
            val extension = extensions.getByType<DetektExtension>()
            configureDetekt(extension)
        }
    }
}