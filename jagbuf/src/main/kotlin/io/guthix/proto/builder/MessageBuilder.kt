package io.guthix.proto.builder

class MessageBuilder(val codecs: MutableList<Codec> = mutableListOf()) {
    fun int(): IntCodec {
        val codec = IntCodec()
        codecs.add(codec)
        return codec
    }

    fun short(): ShortCodec {
        val codec = ShortCodec()
        codecs.add(codec)
        return codec
    }

    fun byte(): ByteCodec {
        val codec = ByteCodec()
        codecs.add(codec)
        return codec
    }

    fun codec(codec: CodecBuilder.()-> Unit) {
        CodecBuilder().codec()
    }
}

class CodecBuilder {
    fun IntCodec.unsigned(): IntCodec = apply {
        signed = false
    }

    fun IntCodec.order(order: ByteOrder): IntCodec = apply {
        this.order = order
    }

    fun IntCodec.asShort(): ShortCodec = ShortCodec().apply {
        signed = this@asShort.signed
        order = this@asShort.order
    }

    fun IntCodec.asByte(): ByteCodec = ByteCodec().apply {
        signed = this@asByte.signed
    }

    fun ShortCodec.unsigned(): ShortCodec = apply {
        signed = false
    }

    fun ShortCodec.order(order: ByteOrder): ShortCodec = apply {
        this.order = order
    }

    fun ShortCodec.asByte(): ByteCodec = ByteCodec().apply {
        signed = this@asByte.signed
        modification = this@asByte.modification
    }

    fun ShortCodec.modify(modification: Modification): ShortCodec = apply {
        this.modification = modification
    }

    fun ByteCodec.unsigned(): ByteCodec = apply {
        signed = false
    }

    fun ByteCodec.modify(modification: Modification): ByteCodec = apply {
        this.modification = modification
    }
}