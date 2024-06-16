plugins {
    alias(libs.plugins.moviesapp.android.library)
    alias(libs.plugins.moviesapp.jvm.retrofit)
    alias(libs.plugins.moviesapp.di.library)
    alias(libs.plugins.secrets)
}

android {
    namespace = "com.dmoyahur.moviesapp.data.remote"

    buildFeatures {
        buildConfig = true
    }

    secrets {
        defaultPropertiesFileName = "secrets.defaults.properties"
    }
}

dependencies {
    implementation(project(":model"))
    implementation(project(":data:repository"))
    implementation(libs.kotlinx.serialization.json)

    testImplementation(project(":testShared"))
    testImplementation(project(":common"))
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.hilt.android.testing)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.roboelectric)
    testImplementation(libs.mockWebServer)
}