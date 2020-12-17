package net.mineutopia.rpg.entity.nms.v1_16_R2.npc

import io.netty.channel.*

import java.net.SocketAddress


class NPCChannel : AbstractChannel(null) {
    override fun config(): ChannelConfig? {
        return null
    }

    override fun isActive(): Boolean {
        return false
    }

    override fun isOpen(): Boolean {
        return false
    }

    override fun metadata(): ChannelMetadata? {
        return null
    }

    @Throws(Exception::class)
    override fun doBeginRead() {
    }

    @Throws(Exception::class)
    override fun doBind(arg0: SocketAddress?) {
    }

    @Throws(Exception::class)
    override fun doClose() {
    }

    @Throws(Exception::class)
    override fun doDisconnect() {
    }

    @Throws(Exception::class)
    override fun doWrite(arg0: ChannelOutboundBuffer) {
    }

    override fun isCompatible(arg0: EventLoop?): Boolean {
        return false
    }

    override fun localAddress0(): SocketAddress? {
        return null
    }

    override fun newUnsafe(): AbstractUnsafe? {
        return null
    }

    override fun remoteAddress0(): SocketAddress? {
        return null
    }
}