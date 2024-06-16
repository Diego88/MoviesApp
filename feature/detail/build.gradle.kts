plugins {
    alias(libs.plugins.moviesapp.android.feature)
    alias(libs.plugins.moviesapp.di.library.compose)
}

android {
    namespace = "com.dmoyahur.moviesapp.feature.detail"
}

dependencies {
    implementation(project(":model"))
    implementation(project(":data:repository"))

    testImplementation(project(":testShared"))
    testImplementation(project(":data:test"))
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.turbine)
    testImplementation(libs.kotlinx.coroutines.test)
}