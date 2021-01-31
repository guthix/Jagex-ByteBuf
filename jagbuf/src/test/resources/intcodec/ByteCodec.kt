package intcodec

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import kotlin.Int

public class ByteCodec(
    public val x: Int
) {
    public fun encode(ctx: ChannelHandlerContext): ByteBuf {
        val buf = ctx.alloc().buffer()
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