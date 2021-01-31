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

import com.squareup.kotlinpoet.*
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import kotlin.script.experimental.annotations.KotlinScript

@KotlinScript(
    fileExtension = "message.kts",
    compilationConfiguration = Configuration::class
)
abstract class MessageDescription {
    val messages = mutableListOf<String>()

    fun message(pkg: String, name: String, build: MessageBuilder.() -> Unit): FileSpec {
        val messageBuilder = MessageBuilder()
        messageBuilder.build()
        val className = ClassName(pkg, name)
        val file = FileSpec.builder(pkg, name).apply {
            indent("    ")
            addType(TypeSpec.classBuilder(className).apply {
                primaryConstructor(FunSpec.constructorBuilder().apply {
                    for(parameter in messageBuilder.properties) {
                        addParameter(parameter.name, parameter.type)
                    }
                    build()
                }.build())
                for(parameter in messageBuilder.properties) {
                    addProperty(PropertySpec.builder(parameter.name, parameter.type).apply {
                        initializer(parameter.name)
                    }.build())
                }
                addFunction(FunSpec.builder("encode").apply {
                    addParameter("ctx", ChannelHandlerContext::class)
                    addStatement("val buf = ctx.alloc().buffer()")
                    for(codec in messageBuilder.codecs) {
                        addStatement("buf.${codec.encoder(codec.property.name)}")
                    }
                    addStatement("return buf")
                    returns(ByteBuf::class)
                }.build())
                addType(TypeSpec.companionObjectBuilder().apply {
                    addFunction(FunSpec.builder("decode").apply {
                        addParameter("buf", ByteBuf::class)
                        for(codec in messageBuilder.codecs) {
                            addStatement("val ${codec.property.name} = buf.${codec.decoder()}${codec.cast()}")
                        }
                        addStatement("return $name(${messageBuilder.properties.joinToString(", ") { it.name }})")
                        returns(className)
                    }.build())
                }.build())
            }.build())
        }.build()
        val builder = StringBuilder()
        file.writeTo(builder)
        val result = builder.toString().removeSuffix("\n")
        messages.add(result)
        return file
    }
}