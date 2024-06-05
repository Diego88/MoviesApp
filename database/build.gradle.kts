plugins {
    alias(libs.plugins.moviesapp.android.library)
    alias(libs.plugins.moviesapp.android.room)
    alias(libs.plugins.moviesapp.di.library)
}

android {
    namespace = "com.dmoyahur.moviesapp.database"
}

dependencies {
    implementation(project(":data:movies"))
    implementation(project(":data:search"))
}