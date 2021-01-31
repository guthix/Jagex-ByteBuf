package intcodec

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import kotlin.Int

public class ShortCodec(
    public val x: Int
) {
    public fun encode(ctx: ChannelHandlerContext): ByteBuf {
        val buf = ctx.alloc().buffer()
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