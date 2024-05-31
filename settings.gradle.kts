@file:Suppress("UnstableApiUsage")

pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MoviesApp"
include(":app")
include(":feature:movies")
include(":feature:search")
include(":feature:detail")
include(":core:model")
include(":core:data")
include(":core:ui")
include(":core:database")
include(":core:network")
include(":domain:movies")
include(":domain:search")
include(":domain:detail")
include(":data:movies")
include(":data:search")