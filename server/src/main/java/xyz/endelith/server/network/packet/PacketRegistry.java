package xyz.endelith.server.network.packet;

import org.jspecify.annotations.Nullable;

import io.netty.buffer.ByteBuf;
import xyz.endelith.cosine.stream.StreamCodec;
import xyz.endelith.server.network.ConnectionState;
import xyz.endelith.server.network.packet.client.ClientPacket;
import xyz.endelith.server.network.packet.client.handshake.ClientHandshakePacket;
import xyz.endelith.server.network.packet.server.ServerPacket;

public sealed interface PacketRegistry<T> permits PacketRegistry.AbstractRegistry {

    T create(int packetId, ByteBuf buf);

    PacketInfo<T> byId(int packetId);

    PacketInfo<T> byClass(Class<?> type);

    ConnectionState state();

    ConnectionSide side();

    record PacketInfo<T>(Class<T> type, int id, StreamCodec<T> codec) {}

    final class ClientHandshake extends Client {
        protected ClientHandshake() {
            super(
                ConnectionState.HANDSHAKE,
                register(ClientHandshakePacket.class, ClientHandshakePacket.SERIALIZER)
            );
        }
    }

    final class ClientStatus extends Client {
        protected ClientStatus() {
            super(
                ConnectionState.STATUS,
                register(null, null)
            );
        }
    }

    final class ClientLogin extends Client {
        protected ClientLogin() {
            super(
                ConnectionState.LOGIN,
                register(null, null)
            );
        }
    }

    final class ClientConfiguration extends Client {
        protected ClientConfiguration() {
            super(
                ConnectionState.CONFIGURATION,
                register(null, null)
            );
        }
    }

    final class ClientPlay extends Client {
        protected ClientPlay() {
            super(
                ConnectionState.PLAY,
                register(null, null)
            );
        }
    }

    final class ServerHandshake extends Server {
        protected ServerHandshake() {
            super(
                ConnectionState.HANDSHAKE,
                register(null, null)
            );
        }
    }

    final class ServerStatus extends Server {
        protected ServerStatus() {
            super(
                ConnectionState.STATUS,
                register(null, null)
            );
        }
    }

    final class ServerLogin extends Server {
        protected ServerLogin() {
            super(
                ConnectionState.LOGIN,
                register(null, null)
            );
        }
    }

    final class ServerConfiguration extends Server {
        protected ServerConfiguration() {
            super(
                ConnectionState.CONFIGURATION,
                register(null, null)
            );
        }
    }

    final class ServerPlay extends Server {
        protected ServerPlay() {
            super(
                ConnectionState.PLAY,
                register(null, null)
            );
        }
    }

    abstract sealed class AbstractRegistry<T> implements PacketRegistry<T> {

        private final ConnectionState state;
        private final ConnectionSide side;
        private final PacketInfo<? extends T>[] packets;

        @SuppressWarnings("unchecked")
        @SafeVarargs
        protected AbstractRegistry(
                ConnectionState state,
                ConnectionSide side,
                Entry<? extends T>... entries
        ) {
            this.state = state;
            this.side = side;
            this.packets = (PacketInfo<? extends T>[]) new PacketInfo[entries.length];

            for (int id = 0; id < entries.length; id++) {
                Entry<? extends T> e = entries[id];
                if (e != null) {
                    packets[id] = new PacketInfo<>(
                            (Class<T>) e.type,
                            id,
                            (StreamCodec<T>) e.codec
                    );
                }
            }
        }

        @Override
        public T create(int packetId, ByteBuf buf) {
            return byId(packetId).codec.read(buf);
        }

        @Override
        public PacketInfo<T> byId(int packetId) {
            if (packetId < 0 || packetId >= packets.length || packets[packetId] == null)
                throw new IllegalStateException(
                        "Unregistered packet id 0x" + Integer.toHexString(packetId)
                );
            return cast(packets[packetId]);
        }

        @Override
        public PacketInfo<T> byClass(Class<?> type) {
            for (@Nullable PacketInfo<? extends T> info : packets) {
                if (info != null && info.type == type) {
                    return cast(info);
                }
            }
        
            throw new IllegalStateException(
                String.format(
                    "Packet %s not valid for %s %s", type.getName(), side, state
                )
            );
        }

        @SuppressWarnings("unchecked")
        private PacketInfo<T> cast(PacketInfo<? extends T> info) {
            return (PacketInfo<T>) info;
        }

        @Override 
        public ConnectionState state() { 
            return state; 
        }

        @Override 
        public ConnectionSide side() { 
            return side; 
        }

        protected record Entry<T>(Class<T> type, StreamCodec<T> codec) {}

        protected static <T> Entry<T> register(Class<T> type, StreamCodec<T> codec) {
            return new Entry<>(type, codec);
        }
    }

    abstract non-sealed class Server extends AbstractRegistry<ServerPacket> {
        
        @SafeVarargs 
        protected Server(ConnectionState state, Entry<? extends ServerPacket>... entries) {
            super(state, ConnectionSide.SERVERBOUND, entries);
        }
    }

    abstract non-sealed class Client extends AbstractRegistry<ClientPacket> {

        @SafeVarargs
        protected Client(ConnectionState state, Entry<? extends ClientPacket>... entries) {
            super(state, ConnectionSide.CLIENTBOUND, entries);
        }
    }

    enum ConnectionSide {
        CLIENTBOUND,
        SERVERBOUND
    }
}
