plugins {
    alias(libs.plugins.moviesapp.android.feature)
    alias(libs.plugins.moviesapp.di.library.compose)
}

android {
    namespace = "com.dmoyahur.moviesapp.feature.detail"
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":domain:detail"))

    testImplementation(project(":core:testing"))
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.turbine)
    testImplementation(libs.kotlinx.coroutines.test)
}