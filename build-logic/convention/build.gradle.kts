plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.detekt.gradlePlugin)
    compileOnly(libs.ktlint.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "moviesapp.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidApplicationCompose") {
            id = "moviesapp.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "moviesapp.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "moviesapp.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidFeature") {
            id = "moviesapp.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("jvmLibrary") {
            id = "moviesapp.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }
        register("androidRoom") {
            id = "moviesapp.android.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }
        register("jvmRetrofit") {
            id = "moviesapp.jvm.retrofit"
            implementationClass = "JvmRetrofitConventionPlugin"
        }
        register("diLibrary") {
            id = "moviesapp.di.library"
            implementationClass = "DiLibraryConventionPlugin"
        }
        register("diLibraryCompose") {
            id = "moviesapp.di.library.compose"
            implementationClass = "DiLibraryComposeConventionPlugin"
        }
        register("kotlinDetekt") {
            id = "moviesapp.kotlin.detekt"
            implementationClass = "DetektConventionPlugin"
        }
        register("ktLint") {
            id = "moviesapp.kotlin.ktlint"
            implementationClass = "KotlinterConventionPlugin"
        }
        register("androidLint") {
            id = "moviesapp.android.lint"
            implementationClass = "AndroidLintConventionPlugin"
        }
    }
}