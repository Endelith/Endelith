package xyz.endelith.server.network.packet;

import io.netty.buffer.ByteBuf;
import java.util.Objects;
import org.jspecify.annotations.Nullable;
import xyz.endelith.cosine.stream.StreamCodec;
import xyz.endelith.server.network.ConnectionState;
import xyz.endelith.server.network.packet.client.ClientPacket;
import xyz.endelith.server.network.packet.client.handshake.ClientHandshakePacket;
import xyz.endelith.server.network.packet.identifer.ClientHandshakePackets;
import xyz.endelith.server.network.packet.server.ServerPacket;

public sealed interface PacketRegistry<T> permits PacketRegistry.AbstractRegistry {

    T create(int packetId, ByteBuf buf);

    PacketInfo<T> byId(int packetId);

    PacketInfo<T> byClass(Class<?> type);

    ConnectionState state();

    ConnectionSide side();
    
    final class ClientHandshake extends Client {
        protected ClientHandshake() {
            super(
                ConnectionState.HANDSHAKE,
                entry(
                    ClientHandshakePackets.CLIENT_INTENTION, 
                    ClientHandshakePacket.class, 
                    ClientHandshakePacket.STREAM_CODEC
                )
            );
        }
    }

    final class ClientStatus extends Client {
        protected ClientStatus() {
            super(ConnectionState.STATUS);
        }
    }

    final class ClientLogin extends Client {
        protected ClientLogin() {
            super(ConnectionState.LOGIN);
        }
    }

    final class ClientConfiguration extends Client {
        protected ClientConfiguration() {
            super(ConnectionState.CONFIGURATION);
        }
    }

    final class ClientPlay extends Client {
        protected ClientPlay() {
            super(ConnectionState.PLAY);
        }
    }

    final class ServerHandshake extends Server {
        protected ServerHandshake() {
            super(ConnectionState.HANDSHAKE);
        }
    }

    final class ServerStatus extends Server {
        protected ServerStatus() {
            super(ConnectionState.STATUS);
        }
    }

    final class ServerLogin extends Server {
        protected ServerLogin() {
            super(ConnectionState.LOGIN);
        }
    }

    final class ServerConfiguration extends Server {
        protected ServerConfiguration() {
            super(ConnectionState.CONFIGURATION);
        }
    }

    final class ServerPlay extends Server {
        protected ServerPlay() {
            super(ConnectionState.PLAY);
        }
    }

    record PacketInfo<T>(Class<T> type, int id, StreamCodec<T> codec) {
        public PacketInfo {
            Objects.requireNonNull(type, "type");
            Objects.requireNonNull(codec, "codec");
        }
    }

    abstract sealed class AbstractRegistry<T> implements PacketRegistry<T> {

        private final PacketInfo<? extends T>[] packets;
        private final ConnectionState state;
        private final ConnectionSide side;

        @SuppressWarnings("unchecked")
        @SafeVarargs
        protected AbstractRegistry(
                ConnectionState state,
                ConnectionSide side,
                Entry<? extends T>... entries
        ) {
            this.state = Objects.requireNonNull(state, "state");
            this.side = Objects.requireNonNull(side, "side");
            this.packets = (PacketInfo<? extends T>[]) new PacketInfo[entries.length];

            for (Entry<? extends T> e : entries) {
                int packetId = e.packetId();

                if (this.packets[packetId] != null) {
                    throw new IllegalStateException(
                            "Duplicate packet id 0x" + Integer.toHexString(packetId)
                    );
                }

                this.packets[packetId] = new PacketInfo<>(
                        (Class<T>) e.type(),
                        packetId,
                        (StreamCodec<T>) e.codec()
                );
            }
        }

        @Override
        public T create(int packetId, ByteBuf buf) {
            return this.byId(packetId).codec().read(buf);
        }

        @Override
        public PacketInfo<T> byId(int packetId) {
            if (packetId < 0
                    || packetId >= this.packets.length
                    || this.packets[packetId] == null) {
                throw new IllegalStateException(
                        "Unregistered packet id 0x" + Integer.toHexString(packetId)
                );
            }
            return this.cast(this.packets[packetId]);
        }

        @Override
        public PacketInfo<T> byClass(Class<?> type) {
            for (@Nullable PacketInfo<? extends T> info : this.packets) {
                if (info != null && info.type() == type) {
                    return this.cast(info);
                }
            }

            throw new IllegalStateException(
                    String.format(
                            "Packet %s not valid for %s %s",
                            type.getName(),
                            this.side,
                            this.state
                    )
            );
        }

        @SuppressWarnings("unchecked")
        private PacketInfo<T> cast(PacketInfo<? extends T> info) {
            return (PacketInfo<T>) info;
        }

        @Override
        public ConnectionState state() {
            return this.state;
        }

        @Override
        public ConnectionSide side() {
            return this.side;
        }

        protected record Entry<T>(
                int packetId,
                Class<T> type,
                StreamCodec<T> codec
        ) {

            protected Entry {
                Objects.requireNonNull(type, "type");
                Objects.requireNonNull(codec, "codec");
            }
        }

        protected static <T> Entry<T> entry(
                int packetId,
                Class<T> type,
                StreamCodec<T> codec
        ) {
            return new Entry<>(packetId, type, codec);
        }
    }

    abstract non-sealed class Server extends AbstractRegistry<ServerPacket> {

        @SafeVarargs
        protected Server(
                ConnectionState state,
                Entry<? extends ServerPacket>... entries
        ) {
            super(state, ConnectionSide.SERVERBOUND, entries);
        }
    }

    abstract non-sealed class Client extends AbstractRegistry<ClientPacket> {

        @SafeVarargs
        protected Client(
                ConnectionState state,
                Entry<? extends ClientPacket>... entries
        ) {
            super(state, ConnectionSide.CLIENTBOUND, entries);
        }
    }

    enum ConnectionSide {
        CLIENTBOUND,
        SERVERBOUND
    }
}
