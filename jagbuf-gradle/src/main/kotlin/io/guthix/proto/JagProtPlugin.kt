package io.guthix.proto

import io.github.classgraph.ClassGraph
import io.guthix.proto.builder.MessageBuilder
import io.guthix.proto.builder.MessageDescription
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.BasePlugin
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.tasks.SourceSetContainer
import java.net.URLClassLoader

class JagProtPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.plugins.withType(JavaPlugin::class.java) {
            val sourceSets = target.properties["sourceSets"] as SourceSetContainer
            val mainSourceSet = sourceSets.getAt("main")
            println(mainSourceSet)
            val urls = mainSourceSet.output.classesDirs.files.map { it.toURI().toURL() }
            val classLoader = URLClassLoader(urls.toTypedArray())
            val protoGenTask = target.task("protoGen") {
                ClassGraph().enableClassInfo().addClassLoader(classLoader).scan().use { scanResult ->
                    val pluginClassList = scanResult.getSubclasses(pkg).directOnly()
                    pluginClassList.forEach {
                        it.loadClass(MessageDescription::class.java).getDeclaredConstructor().newInstance()
                    }
                    println("Loaded ${pluginClassList.size} scripts")
                }
            }
            val classesTask = target.tasks.getByName("compileKotlin")
            protoGenTask.group = BasePlugin.BUILD_GROUP
            protoGenTask.dependsOn(classesTask)
        }
    }

    companion object {
        const val pkg = "io.guthix.proto.builder.MessageDescription"
    }
}