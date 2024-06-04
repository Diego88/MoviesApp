plugins {
    alias(libs.plugins.moviesapp.android.library)
    alias(libs.plugins.moviesapp.android.room)
    alias(libs.plugins.moviesapp.jvm.retrofit)
    alias(libs.plugins.moviesapp.di.library)
}

android {
    namespace = "com.dmoyahur.moviesapp.data.movies"
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:data"))
    implementation(project(":core:network"))
    implementation(project(":domain:movies"))

    testImplementation(libs.junit)
}