plugins {
    alias(libs.plugins.moviesapp.android.library)
    alias(libs.plugins.moviesapp.android.library.compose)
    alias(libs.plugins.moviesapp.di.library)
}

android {
    namespace = "com.dmoyahur.moviesapp.testShared"
}

dependencies {
    implementation(project(":model"))
    implementation(libs.androidx.test.rules)
    implementation(libs.hilt.android.testing)
    implementation(libs.kotlinx.coroutines.test)
    implementation(libs.junit)
    implementation(libs.androidx.compose.ui.test)
    debugImplementation(libs.androidx.ui.test.manifest)
}