package com.dmoyahur.moviesapp

import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.language.base.plugins.LifecycleBasePlugin
import org.jmailen.gradle.kotlinter.KotlinterExtension
import org.jmailen.gradle.kotlinter.tasks.FormatTask
import org.jmailen.gradle.kotlinter.tasks.LintTask

internal fun Project.configureKotlinter() = extensions.configure<KotlinterExtension> {
    val reportsPath = file(buildDirectory.file("reports/ktlint/$packageName"))
    group = LifecycleBasePlugin.VERIFICATION_GROUP
    failBuildWhenCannotAutoFormat = false
    ignoreFailures = false
    reporters = arrayOf("html", "checkstyle", "sarif")

    tasks.register("ktLint", LintTask::class.java) {
        source(files("src"))
        reports.set(
            mapOf(
                "html" to file("$reportsPath/ktlint-report.html"),
                "checkstyle" to file("$reportsPath/ktlint-report.checkstyle"),
                "sarif" to file("$reportsPath/ktlint-report.sarif")
            )
        )
    }

    tasks.register("ktFormat", FormatTask::class.java) {
        group = "formatting"
        source(files("src"))
        report.set(file("$reportsPath/format-report.txt"))
    }
}