package intcodec

import io.netty.buffer.ByteBuf
import io.netty.buffer.ByteBufAllocator
import kotlin.Int

public class MultiProperty(
    public val x: Int,
    public val y: Int,
    public val z: Int
) {
    public fun encode(alloc: ByteBufAllocator = ByteBufAllocator.DEFAULT): ByteBuf {
        val buf = alloc.buffer()
        buf.writeInt(x)
        buf.writeInt(y)
        buf.writeInt(z)
        return buf
    }

    public companion object {
        public fun decode(buf: ByteBuf): MultiProperty {
            val x = buf.readInt()
            val y = buf.readInt()
            val z = buf.readInt()
            return MultiProperty(x, y, z)
        }
    }
}