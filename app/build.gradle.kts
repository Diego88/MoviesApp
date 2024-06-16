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
        testInstrumentationRunner = "com.dmoyahur.moviesapp.testShared.HiltTestRunner"

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
    implementation(project(":model"))
    implementation(project(":data:local"))
    implementation(project(":data:remote"))
    implementation(project(":data:repository"))
    implementation(project(":common"))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.material.icons)
    implementation(libs.androidx.core.splashscreen)

    ksp(libs.hilt.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(project(":testShared"))
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.hilt.android.testing)

    kspTest(libs.hilt.compiler)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}