package io.guthix.proto.builder

import kotlin.reflect.KClass
import kotlin.reflect.KProperty

class MessageBuilder(
    val properties: MutableList<Property> = mutableListOf(),
    val codecs: MutableList<Codec> = mutableListOf()
) {
    fun codec(codec: CodecBuilder.()-> Unit) {
        CodecBuilder(codecs).codec()
    }

    inner class IntProperty(name: String = "") : PrimitiveProperty(name) {
        override val type: KClass<out Number> = Int::class

        override fun cast(): String = "toInt()"

        operator fun getValue(thisRef: Any?, property: KProperty<*>): IntProperty {
            if(name.isEmpty()) {
                name = property.name
                properties.add(this)
            }
            return this
        }
    }

    inner class ShortProperty(name: String) : PrimitiveProperty(name) {
        override val type: KClass<out Number> = Short::class

        override fun cast(): String = "toShort()"

        operator fun getValue(thisRef: Any?, property: KProperty<*>): ShortProperty {
            if(name.isEmpty()) {
                name = property.name
                properties.add(this)
            }
            return this
        }
    }

    inner class ByteProperty(name: String) : PrimitiveProperty(name) {
        override val type: KClass<out Number> = Byte::class

        override fun cast(): String = "toByte()"

        operator fun getValue(thisRef: Any?, property: KProperty<*>): ByteProperty {
            if(name.isEmpty()) {
                name = property.name
                properties.add(this)
            }
            return this
        }
    }
}

class CodecBuilder(val codecs: MutableList<Codec>) {
    fun MessageBuilder.IntProperty.asInt(): IntCodec {
        val codec = IntCodec(this)
        codecs.add(codec)
        return codec
    }

    fun MessageBuilder.IntProperty.asShort(): ShortCodec {
        val codec = ShortCodec(this)
        codecs.add(codec)
        return codec
    }

    fun MessageBuilder.IntProperty.asByte(): ByteCodec {
        val codec = ByteCodec(this)
        codecs.add(codec)
        return codec
    }

    fun IntCodec.unsigned(): IntCodec = apply {
        signed = false
    }

    fun IntCodec.order(order: ByteOrder): IntCodec = apply {
        this.order = order
    }

    fun MessageBuilder.ShortProperty.asShort(): ShortCodec {
        val codec = ShortCodec(this)
        codecs.add(codec)
        return codec
    }

    fun MessageBuilder.ShortProperty.asByte(): ByteCodec {
        val codec = ByteCodec(this)
        codecs.add(codec)
        return codec
    }

    fun ShortCodec.unsigned(): ShortCodec = apply {
        require(property.type == Int::class)
        signed = false
    }

    fun ShortCodec.order(order: ByteOrder): ShortCodec = apply {
        this.order = order
    }

    fun ShortCodec.modify(modification: Modification): ShortCodec = apply {
        this.modification = modification
    }

    fun MessageBuilder.ByteProperty.asByte(): ByteCodec {
        val codec = ByteCodec(this)
        codecs.add(codec)
        return codec
    }

    fun ByteCodec.unsigned(): ByteCodec = apply {
        require(property.type == Int::class || property.type == Short::class)
        signed = false
    }

    fun ByteCodec.modify(modification: Modification): ByteCodec = apply {
        this.modification = modification
    }
}