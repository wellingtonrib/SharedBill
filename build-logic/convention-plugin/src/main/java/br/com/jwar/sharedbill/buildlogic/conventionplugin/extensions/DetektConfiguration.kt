package br.com.jwar.sharedbill.buildlogic.conventionplugin.extensions

import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import io.gitlab.arturbosch.detekt.report.ReportMergeTask
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType

internal fun Project.configureDetekt(extension: DetektExtension) = extension.apply {

    config.setFrom(file("${project.rootDir}/config/detekt/detekt.yml"))
    buildUponDefaultConfig = true
    basePath = rootProject.projectDir.absolutePath

    tasks.named<Detekt>("detekt") {
        reports {
            sarif.required.set(true)
        }
    }

    val reportMerge = if (!rootProject.tasks.names.contains("reportMerge")) {
        rootProject.tasks.register("reportMerge", ReportMergeTask::class) {
            output.set(rootProject.layout.buildDirectory.file("reports/detekt/merge.sarif"))
        }
    } else {
        rootProject.tasks.named("reportMerge") as TaskProvider<ReportMergeTask>
    }

    tasks.withType<Detekt> detekt@{
        finalizedBy(reportMerge)
        reportMerge.configure {
            input.from(this@detekt.sarifReportFile)
        }
    }

    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
    dependencies {
        "detektPlugins"(libs.findLibrary("detekt-formatting").get())
    }
}