package io.guthix.proto

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import kotlin.Int

public class Foo(
    public val x: Int
) {
    public fun encode(ctx: ChannelHandlerContext): ByteBuf {
        val buf = ctx.alloc().buffer()
        buf.writeInt(x)
        return buf
    }

    public companion object {
        public fun decode(buf: ByteBuf): Foo {
            val x = buf.readInt()
            return Foo(x)
        }
    }
}