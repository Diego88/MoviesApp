plugins {
    alias(libs.plugins.moviesapp.android.library)
    alias(libs.plugins.moviesapp.jvm.retrofit)
    alias(libs.plugins.moviesapp.di.library)
    alias(libs.plugins.secrets)
}

android {
    namespace = "com.dmoyahur.moviesapp.core.network"

    buildFeatures {
        buildConfig = true
    }

    secrets {
        defaultPropertiesFileName = "secrets.defaults.properties"
    }
}