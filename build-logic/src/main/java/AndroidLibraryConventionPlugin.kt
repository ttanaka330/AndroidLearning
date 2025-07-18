import com.android.build.gradle.LibraryExtension
import com.github.ttanaka330.android.buildlogic.configureKotlinAndroid
import com.github.ttanaka330.android.buildlogic.libs
import com.github.ttanaka330.android.buildlogic.version
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "com.android.library")
            apply(plugin = "org.jetbrains.kotlin.android")

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = libs.version("targetSdk").toInt()
            }
        }
    }
}
