package br.com.jwar.sharedbill.buildlogic.conventionplugin.extensions

import kotlinx.kover.gradle.plugin.dsl.KoverReportExtension
import org.gradle.api.Project

val excludedKoverSubProjects = listOf(
    ":core:testing",
    ":core:utility",
    ":core:common",
)

internal fun Project.configureKover(extension: KoverReportExtension) = extension.apply {
    filters {
        excludes {
            classes(
                "*_ModuleKt",
                "*_Factory",
                "*_Factory_Impl",
                "*_Factory$*",
                "*_Module",
                "*_Module$*",
                "*Module_Provides*",
                "Dagger*Component*",
                "*ComposableSingletons$*",
                "*_AssistedFactory_Impl*",
                "*BuildConfig",
            )
            annotatedBy(
                "androidx.compose.ui.tooling.preview.Preview",
                "*Generated"
            )
        }
    }
}

fun Project.applyKoverDependenciesForSubProjects() {
    project.rootProject.subprojects
        .filter { it.project.projectDir.resolve("build.gradle.kts").exists() }
        .map { it.path }
        .sorted()
        .filter { it !in excludedKoverSubProjects }
        .forEach {
            dependencies.add("kover", project(it))
        }
}