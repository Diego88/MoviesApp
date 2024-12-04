import com.android.build.api.dsl.ApplicationExtension
import com.dmoyahur.moviesapp.Versions
import com.dmoyahur.moviesapp.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("moviesapp.kotlin.detekt")
                apply("moviesapp.kotlin.ktlint")
                apply("moviesapp.android.lint")
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = Versions.TARGET_SDK_VERSION
            }
        }
    }
}