package xyz.endelith.server.network.netty.transport;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.MultiThreadIoEventLoopGroup;
import io.netty.channel.epoll.EpollIoHandler;
import io.netty.channel.kqueue.KQueueIoHandler;
import io.netty.channel.nio.NioIoHandler;

public final class NettyTransportFactory {
    
    private NettyTransportFactory() {}

    public static EventLoopGroup nio() {
        return new MultiThreadIoEventLoopGroup(
            NioIoHandler.newFactory()
        );
    }

    public static EventLoopGroup epoll() {
        return new MultiThreadIoEventLoopGroup(
            EpollIoHandler.newFactory()
        );
    }

    public static EventLoopGroup kqueue() {
        return new MultiThreadIoEventLoopGroup(
            KQueueIoHandler.newFactory()
        );
    }
}
