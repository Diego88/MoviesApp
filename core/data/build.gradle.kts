plugins {
    alias(libs.plugins.moviesapp.jvm.library)
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

dependencies {

    implementation(project(":core:model"))
    implementation(libs.kotlinx.serialization.json)
}