package intcodec

import io.netty.buffer.ByteBuf
import io.netty.buffer.ByteBufAllocator
import kotlin.Int

public class ByteCodec(
    public val x: Int
) {
    public fun encode(alloc: ByteBufAllocator = ByteBufAllocator.DEFAULT): ByteBuf {
        val buf = alloc.buffer()
        buf.writeByte(x)
        return buf
    }

    public companion object {
        public fun decode(buf: ByteBuf): ByteCodec {
            val x = buf.readByte().toInt()
            return ByteCodec(x)
        }
    }
}