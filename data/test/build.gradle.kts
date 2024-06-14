plugins {
    alias(libs.plugins.moviesapp.android.library)
    alias(libs.plugins.moviesapp.di.library)
}

android {
    namespace = "com.dmoyahur.moviesapp.data.test"
}

dependencies {
    implementation(project(":model"))
    implementation(project(":data:repository"))
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.hilt.android.testing)
}