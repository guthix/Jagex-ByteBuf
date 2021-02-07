package io.guthix.proto

import io.github.classgraph.ClassGraph
import io.guthix.proto.builder.MessageDescription
import org.gradle.api.Action
import org.gradle.api.Task
import java.io.File
import java.net.URLClassLoader
import java.nio.file.Files

class ProtGenAction(private val classLoader: URLClassLoader, private val generatedFolder: File) : Action<Task> {
    override fun execute(t: Task) {
        ClassGraph().enableClassInfo().addClassLoader(classLoader).scan().use { scanResult ->
            val pluginClassList = scanResult.getSubclasses(JagProtPlugin.pkg).directOnly()
            val messageDescription = pluginClassList.map {
                it.loadClass(MessageDescription::class.java).getDeclaredConstructor().newInstance()
            }
            messageDescription.forEach { description ->
                description.messages.forEach {
                    val file = generatedFolder.resolve("${it.name}.kt")
                    file.createNewFile()
                    Files.writeString(file.toPath(), "$it")
                }
            }
            println("Loaded ${messageDescription.size} scripts")
        }
    }
}