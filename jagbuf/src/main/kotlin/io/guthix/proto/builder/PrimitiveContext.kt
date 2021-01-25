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

enum class ByteOrder(val writeName: String) {
    BIG_ENDIAN(writeName = ""),
    MIDDLE_ENDIAN(writeName = "ME"),
    INVERSE_MIDDLE_ENDIAN(writeName = "IME"),
    LITTLE_ENDIAN(writeName = "LE")
}

enum class Modification(val writeName: String) {
    NONE(writeName = ""),
    ADD(writeName = "Add"),
    SUB(writeName = "Sub"),
    NEG(writeName = "Neg")
}

interface Codec

interface CodecWriter {
    fun encoder(value: Int): String

    fun decoder(): String
}

interface PrimitiveCodec : CodecWriter {
    var signed: Boolean
}

interface OrderedCodec : Codec {
    var order: ByteOrder
}

interface ModifiableCodec : Codec {
    var modification: Modification
}

class IntCodec : OrderedCodec, PrimitiveCodec {
    override var signed = true

    override var order: ByteOrder = ByteOrder.BIG_ENDIAN

    override fun encoder(value: Int): String = "writeInt${order.writeName}($value)"

    override fun decoder(): String = "writeInt${order.writeName}()"
}

class ShortCodec : OrderedCodec, ModifiableCodec, PrimitiveCodec {
    override var signed = true

    override var order: ByteOrder = ByteOrder.BIG_ENDIAN

    override var modification: Modification = Modification.NONE

    override fun encoder(value: Int): String = "writeInt${modification.writeName}${order.writeName}($value)"

    override fun decoder(): String = "writeInt${modification.writeName}${order.writeName}()"
}

class ByteCodec : ModifiableCodec, PrimitiveCodec {
    override var signed = true

    override var modification: Modification = Modification.NONE

    override fun encoder(value: Int): String = "writeInt${modification.writeName}($value)"

    override fun decoder(): String = "writeInt${modification.writeName}()"
}