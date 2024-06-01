plugins {
    alias(libs.plugins.moviesapp.jvm.library)
    alias(libs.plugins.moviesapp.di.library)
}

dependencies {
    implementation(project(":core:model"))
    implementation(libs.kotlinx.coroutines.core)

    testImplementation(libs.junit)
}