package com.dmoyahur.moviesapp

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.file.DirectoryProperty
import org.gradle.kotlin.dsl.getByType

val Project.libs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

val Project.buildDirectory: DirectoryProperty
    get() = rootProject.layout.buildDirectory

val Project.packageName: String
    get() = "${group.toString().lowercase()}.$name"