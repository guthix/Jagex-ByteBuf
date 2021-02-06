package intcodec

import io.netty.buffer.ByteBuf
import io.netty.buffer.ByteBufAllocator
import kotlin.Int

public class ShortCodec(
    public val x: Int
) {
    public fun encode(alloc: ByteBufAllocator = ByteBufAllocator.DEFAULT): ByteBuf {
        val buf = alloc.buffer()
        buf.writeShort(x)
        return buf
    }

    public companion object {
        public fun decode(buf: ByteBuf): ShortCodec {
            val x = buf.readShort().toInt()
            return ShortCodec(x)
        }
    }
}