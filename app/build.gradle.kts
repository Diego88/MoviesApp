plugins {
    alias(libs.plugins.moviesapp.android.application)
    alias(libs.plugins.moviesapp.android.application.compose)
    alias(libs.plugins.moviesapp.di.library.compose)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.dmoyahur.moviesapp"

    defaultConfig {
        applicationId = "com.dmoyahur.moviesapp"
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        compose = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":feature:movies"))
    implementation(project(":feature:search"))
    implementation(project(":feature:detail"))
    implementation(project(":core:model"))
    implementation(project(":core:ui"))
    implementation(project(":core:database"))
    implementation(project(":core:network"))
    implementation(project(":core:testing"))
    implementation(project(":domain:movies"))
    implementation(project(":domain:search"))
    implementation(project(":domain:detail"))
    implementation(project(":data:movies"))
    implementation(project(":data:search"))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.material.icons)
    implementation(libs.androidx.core.splashscreen)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}