package xyz.endelith.server.network.netty.transport;

public enum NettyTransportSelector { 
    NIO(NettyTransportType.NIO),
    EPOLL(NettyTransportType.EPOLL),
    KQUEUE(NettyTransportType.KQUEUE),
    AUTO(NettyTransportType.select());

    private final NettyTransportType transportType;

    NettyTransportSelector(NettyTransportType transportType) {
        this.transportType = transportType;
    }

    public NettyTransportType transportType() {
        return transportType;
    }
}
