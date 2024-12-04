package com.dmoyahur.moviesapp

import com.android.build.api.dsl.Lint
import org.gradle.api.Project
import org.gradle.language.base.plugins.LifecycleBasePlugin

internal fun Lint.configureLint(target: Project) {
    with(target) {
        val path = path.replace(":", ".")
        val reportsPath = file(buildDirectory.file("reports/lint/moviesapp$path"))

        group = LifecycleBasePlugin.VERIFICATION_GROUP
        checkDependencies = true
        abortOnError = false
        lintConfig = file("$rootDir/config/lint/lint-rules.xml")
        xmlOutput = file("$reportsPath/lint-report.xml")
        htmlOutput = file("$reportsPath/lint-report.html")
        sarifOutput = file("$reportsPath/lint-report.sarif")
    }
}