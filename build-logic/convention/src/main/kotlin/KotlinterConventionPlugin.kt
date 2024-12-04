import com.dmoyahur.moviesapp.configureKotlinter
import org.gradle.api.Plugin
import org.gradle.api.Project

class KotlinterConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("org.jmailen.kotlinter")
            }

            configureKotlinter()
        }
    }
}