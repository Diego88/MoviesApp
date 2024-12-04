import com.dmoyahur.moviesapp.configureDetekt
import com.dmoyahur.moviesapp.libs
import org.gradle.api.Plugin
import org.gradle.api.Project

class DetektConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(
                libs.findLibrary("detekt-gradlePlugin").get().get().group.toString()
            )

            configureDetekt()
        }
    }
}