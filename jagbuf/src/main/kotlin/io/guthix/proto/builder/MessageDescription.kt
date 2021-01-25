/*
 * Copyright 2018-2021 Guthix
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.guthix.proto.builder

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeSpec
import kotlin.script.experimental.annotations.KotlinScript

@KotlinScript(
    fileExtension = "message.kts",
    compilationConfiguration = Configuration::class
)
abstract class MessageDescription {
    fun message(pkg: String, name: String, build: MessageBuilder.() -> Unit)  {
        val messageBuilder = MessageBuilder()
        messageBuilder.build()
        val file = FileSpec.builder(pkg, name).apply {
            addType(TypeSpec.classBuilder(name).apply {
                primaryConstructor(FunSpec.constructorBuilder().apply {
                    addParameter("test", String::class)
                    build()
                }.build())
            }.build())
        }.build()
        file.writeTo(System.out)
    }
}