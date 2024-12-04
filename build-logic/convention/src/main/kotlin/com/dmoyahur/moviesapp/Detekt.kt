package com.dmoyahur.moviesapp

import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Project
import org.gradle.api.tasks.SourceTask
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.gradle.language.base.plugins.LifecycleBasePlugin

internal fun Project.configureDetekt() = extensions.configure<DetektExtension> {
    val projectSource = files(projectDir)
    val reportsPath = file(buildDirectory.file("reports/detekt/$packageName"))
    val configFile = files("$rootDir/config/detekt/detekt-rules.yml")
    val baselineFile = file("$rootDir/config/detekt/baseline.xml")
    val kotlinFiles = "**/*.kt"
    val kotlinScriptFiles = "**/*.kts"
    val resourceFiles = "**/resources/**"
    val buildFiles = "**/build/**"

    val detektConfig: SourceTask.() -> Unit = {
        group = LifecycleBasePlugin.VERIFICATION_GROUP
        include(kotlinFiles, kotlinScriptFiles)
        exclude(resourceFiles, buildFiles)
    }

    tasks.register("detektAll", Detekt::class.java) {
        description = "Runs detekt on all modules"
        parallel = true
        // If ignoreFailures = false, build fails when find errors in a module
        // Execute detekt with --continue option in order to check all modules errors
        ignoreFailures = false
        buildUponDefaultConfig = true
        setSource(projectSource)
        if (baselineFile.exists()) baseline.set(baselineFile)
        config.setFrom(configFile)
        reportsDir.set(reportsPath)
        reports.txt.required.set(false)
        reports.md.required.set(false)
        reports.html.outputLocation.set(file("$reportsPath/detekt-report.html"))
        reports.xml.outputLocation.set(file("$reportsPath/detekt-report.xml"))
        reports.sarif.outputLocation.set(file("$reportsPath/detekt-report.sarif"))
        detektConfig()
    }

    tasks.register("detektCreateBaseline", DetektCreateBaselineTask::class.java) {
        description = "Create global baseline file with all modules errors"
        buildUponDefaultConfig.set(true)
        ignoreFailures.set(false)
        parallel.set(true)
        setSource(files(rootDir))
        config.setFrom(configFile)
        baseline.set(baselineFile)
        detektConfig()
    }

    tasks.withType<Detekt>()
}
