plugins {
    alias(libs.plugins.moviesapp.android.library)
    alias(libs.plugins.moviesapp.android.room)
    alias(libs.plugins.moviesapp.di.library)
}

android {
    namespace = "com.dmoyahur.moviesapp.data.local"
}

dependencies {
    implementation(project(":model"))
    implementation(project(":data:repository"))

    testImplementation(libs.junit)
}