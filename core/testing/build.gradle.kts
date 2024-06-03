plugins {
    alias(libs.plugins.moviesapp.jvm.library)
}

dependencies {

    implementation(project(":core:data"))
    implementation(project(":core:model"))
    implementation(libs.kotlinx.coroutines.test)
    implementation(libs.junit)
}