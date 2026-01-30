package xyz.endelith.server.network.packet;

import io.netty.buffer.ByteBuf;
import xyz.endelith.server.network.ConnectionState;
import xyz.endelith.server.network.packet.client.ClientPacket;
import xyz.endelith.server.network.packet.server.ServerPacket;

public sealed interface PacketParser<T> {

    PacketRegistry<T> handshake();

    PacketRegistry<T> status();
    
    PacketRegistry<T> login();
    
    PacketRegistry<T> configuration();
    
    PacketRegistry<T> play();

    default T parse(ConnectionState state, int packetId, ByteBuf buf) {
        final PacketRegistry<T> registry = stateRegistry(state);
        return registry.create(packetId, buf);
    }

    default PacketRegistry<T> stateRegistry(ConnectionState state) {
        return switch (state) {
            case HANDSHAKE -> handshake();
            case STATUS -> status();
            case LOGIN -> login();
            case CONFIGURATION -> configuration();
            case PLAY -> play();
        };
    }

    record Client(
        PacketRegistry<ClientPacket> handshake,
        PacketRegistry<ClientPacket> status,
        PacketRegistry<ClientPacket> login,
        PacketRegistry<ClientPacket> configuration,
        PacketRegistry<ClientPacket> play
    ) implements PacketParser<ClientPacket> {
        public Client() {
            this(
                new PacketRegistry.ClientHandshake(),
                new PacketRegistry.ClientStatus(),
                new PacketRegistry.ClientLogin(),
                new PacketRegistry.ClientConfiguration(),
                new PacketRegistry.ClientPlay()
            );
        }
    }

    record Server(
        PacketRegistry<ServerPacket> handshake,
        PacketRegistry<ServerPacket> status,
        PacketRegistry<ServerPacket> login,
        PacketRegistry<ServerPacket> configuration,
        PacketRegistry<ServerPacket> play
    ) implements PacketParser<ServerPacket> {
        public Server() {
            this(
                new PacketRegistry.ServerHandshake(),
                new PacketRegistry.ServerStatus(),
                new PacketRegistry.ServerLogin(),
                new PacketRegistry.ServerConfiguration(),
                new PacketRegistry.ServerPlay()
            );
        }
    }
}
