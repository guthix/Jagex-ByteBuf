package io.guthix.proto.builder

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import java.nio.file.Path

abstract class MessageDescriptionTest(body: StringSpec.() -> Unit = {}) : StringSpec(body) {
    abstract val folder: String

    fun doTest(filename: String, generated: MessageDescription.() -> Unit) {
        val description = object : MessageDescription() {init { generated() } }
        val message = description.messages.map { it.toString().removeSuffix("\n") }.first()
        val expectedFile = Path.of(javaClass.getResource("/$folder/$filename.kt").toURI()).toFile()
            .readLines().joinToString("\n")
        message shouldBe expectedFile
    }
}