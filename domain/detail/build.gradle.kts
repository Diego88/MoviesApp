plugins {
    alias(libs.plugins.moviesapp.jvm.library)
    alias(libs.plugins.moviesapp.di.library)
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":domain:movies"))
    implementation(project(":domain:search"))
    implementation(libs.kotlinx.coroutines.core)

    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
}