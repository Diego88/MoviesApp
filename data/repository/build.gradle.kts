plugins {
    alias(libs.plugins.moviesapp.android.library)
    alias(libs.plugins.moviesapp.di.library)
}

android {
    namespace = "com.dmoyahur.moviesapp.data.repository"
}

dependencies {
    implementation(project(":model"))
    implementation(libs.kotlinx.coroutines.core)

    testImplementation(project(":testShared"))
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
}