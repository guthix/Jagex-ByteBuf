package intcodec

import io.netty.buffer.ByteBuf
import io.netty.buffer.ByteBufAllocator
import kotlin.Int

public class SingleProperty(
    public val x: Int
) {
    public fun encode(alloc: ByteBufAllocator = ByteBufAllocator.DEFAULT): ByteBuf {
        val buf = alloc.buffer()
        buf.writeInt(x)
        return buf
    }

    public companion object {
        public fun decode(buf: ByteBuf): SingleProperty {
            val x = buf.readInt()
            return SingleProperty(x)
        }
    }
}