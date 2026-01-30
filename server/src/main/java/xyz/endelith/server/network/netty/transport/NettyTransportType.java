package xyz.endelith.server.network.netty.transport;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.kqueue.KQueue;
import io.netty.channel.kqueue.KQueueServerSocketChannel;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.util.function.Supplier;

public enum NettyTransportType {
    NIO(NettyTransportFactory::nio, NioServerSocketChannel.class, true),
    EPOLL(NettyTransportFactory::epoll, EpollServerSocketChannel.class, Epoll.isAvailable()),
    KQUEUE(NettyTransportFactory::kqueue, KQueueServerSocketChannel.class, KQueue.isAvailable());

    private final Supplier<EventLoopGroup> eventLoopSupplier;
    private final Class<? extends ServerSocketChannel> socketChannel;

    private final boolean available;

    NettyTransportType(
            Supplier<EventLoopGroup> eventLoopSupplier,
            Class<? extends ServerSocketChannel> socketChannel,
            boolean available
    ) {
        this.eventLoopSupplier = eventLoopSupplier;
        this.socketChannel = socketChannel;
        this.available = available;
    }

    public EventLoopGroup createEventLoop() {
        return this.eventLoopSupplier.get();
    }

    public Class<? extends ServerSocketChannel> getSocketChannelClass() {
        return this.socketChannel;
    }

    public boolean isAvailable() {
        return this.available;
    }

    public static NettyTransportType select() {
        if (Epoll.isAvailable()) {
            return NettyTransportType.EPOLL;
        }

        if (KQueue.isAvailable()) {
            return NettyTransportType.KQUEUE;
        }

        return NettyTransportType.NIO;
    }
}
