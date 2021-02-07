package io.guthix.proto

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.BasePlugin
import org.gradle.api.tasks.SourceSetContainer
import java.net.URLClassLoader

class JagProtPlugin : Plugin<Project> {
    @Suppress("UnstableApiUsage")
    override fun apply(target: Project) {
        val protoGenTask = target.tasks.register("protoGen") {
            val mainSourceSet = (target.properties["sourceSets"] as SourceSetContainer).getAt("main")
            val generatedFolder = target.buildDir.resolve("generated").resolve("sources").resolve("proto")
            generatedFolder.mkdirs()
            mainSourceSet.java.srcDir(generatedFolder)
            val urls = mainSourceSet.output.classesDirs.files.map { it.toURI().toURL() }
            val classLoader = URLClassLoader(urls.toTypedArray())
            mustRunAfter("compileKotlin")
            doFirst(ProtGenAction(classLoader, generatedFolder))
        }.get()
        protoGenTask.group  = BasePlugin.BUILD_GROUP
    }

    companion object {
        const val pkg = "io.guthix.proto.builder.MessageDescription"
    }
}