plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.moviesapp.android.library.compose)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.dmoyahur.moviesapp.common"
}

dependencies {
    implementation(project(":model"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.compose)
    implementation(libs.material)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
}