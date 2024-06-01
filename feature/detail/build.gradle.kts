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
}