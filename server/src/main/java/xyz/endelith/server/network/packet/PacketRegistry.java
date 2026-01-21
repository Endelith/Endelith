package xyz.endelith.server.network.packet;

import org.jspecify.annotations.Nullable;

import io.netty.buffer.ByteBuf;
import xyz.endelith.cosine.stream.StreamCodec;
import xyz.endelith.server.network.ConnectionState;
import xyz.endelith.server.network.packet.client.ClientPacket;
import xyz.endelith.server.network.packet.client.handshake.ClientHandshakePacket;
import xyz.endelith.server.network.packet.client.login.ClientLoginAcknowledgedPacket;
import xyz.endelith.server.network.packet.client.login.ClientLoginEncryptionResponsePacket;
import xyz.endelith.server.network.packet.client.login.ClientLoginPluginResponsePacket;
import xyz.endelith.server.network.packet.client.login.ClientLoginStartPacket;
import xyz.endelith.server.network.packet.client.status.ClientStatusPingRequestPacket;
import xyz.endelith.server.network.packet.client.status.ClientStatusRequestPacket;
import xyz.endelith.server.network.packet.server.ServerPacket;
import xyz.endelith.server.network.packet.server.configuration.ServerConfigurationFinishPacket;
import xyz.endelith.server.network.packet.server.configuration.ServerConfigurationRegistryDataPacket;
import xyz.endelith.server.network.packet.server.login.ServerLoginDisconnectPacket;
import xyz.endelith.server.network.packet.server.login.ServerLoginEncryptionRequestPacket;
import xyz.endelith.server.network.packet.server.login.ServerLoginPluginRequestPacket;
import xyz.endelith.server.network.packet.server.login.ServerLoginSetCompressionPacket;
import xyz.endelith.server.network.packet.server.login.ServerLoginSuccessPacket;
import xyz.endelith.server.network.packet.server.status.ServerStatusPongResponsePacket;
import xyz.endelith.server.network.packet.server.status.ServerStatusResponsePacket;

@SuppressWarnings("unchecked")
public sealed interface PacketRegistry<T> permits PacketRegistry.AbstractRegistry {
    
    @SuppressWarnings("rawtypes")
    final static AbstractRegistry.Entry SKIP = new AbstractRegistry.Entry<>(null, null); //TODO: temporarily added to help ADHD

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
                register(ClientStatusRequestPacket.class, ClientStatusRequestPacket.SERIALIZER),
                register(ClientStatusPingRequestPacket.class, ClientStatusPingRequestPacket.SERIALIZER)
            );
        }
    }

    final class ClientLogin extends Client {
        protected ClientLogin() {
            super(
                ConnectionState.LOGIN,
                register(ClientLoginStartPacket.class, ClientLoginStartPacket.SERIALIZER),
                register(ClientLoginEncryptionResponsePacket.class, ClientLoginEncryptionResponsePacket.SERIALIZER),
                register(ClientLoginPluginResponsePacket.class, ClientLoginPluginResponsePacket.SERIALIZER),
                register(ClientLoginAcknowledgedPacket.class, ClientLoginAcknowledgedPacket.SERIALIZER)
            );
        }
    }

    final class ClientConfiguration extends Client {
        protected ClientConfiguration() {
            super(
                ConnectionState.CONFIGURATION
            );
        }
    }

    final class ClientPlay extends Client {
        protected ClientPlay() {
            super(
                ConnectionState.PLAY
            );
        }
    }

    final class ServerHandshake extends Server {
        protected ServerHandshake() {
            super(ConnectionState.HANDSHAKE);
        }
    }

    final class ServerStatus extends Server {
        protected ServerStatus() {
            super(
                ConnectionState.STATUS,
                register(ServerStatusResponsePacket.class, ServerStatusResponsePacket.SERIALIZER),
                register(ServerStatusPongResponsePacket.class, ServerStatusPongResponsePacket.SERIALIZER)
            );
        }
    }

    final class ServerLogin extends Server {
        protected ServerLogin() {
            super(
                ConnectionState.LOGIN,
                register(ServerLoginDisconnectPacket.class, ServerLoginDisconnectPacket.SERIALIZER),
                register(ServerLoginEncryptionRequestPacket.class, ServerLoginEncryptionRequestPacket.SERIALIZER),
                register(ServerLoginSuccessPacket.class, ServerLoginSuccessPacket.SERIALIZER),
                register(ServerLoginSetCompressionPacket.class, ServerLoginSetCompressionPacket.SERIALIZER),
                register(ServerLoginPluginRequestPacket.class, ServerLoginPluginRequestPacket.SERIALIZER)
            );
        }
    }

    final class ServerConfiguration extends Server {
        protected ServerConfiguration() {
            super(
                ConnectionState.CONFIGURATION,
                SKIP, //Cookie request
                SKIP, //Plugin message
                SKIP, //Disconnect
                register(ServerConfigurationFinishPacket.class, ServerConfigurationFinishPacket.SERIALIZER),
                SKIP, //Keep alive
                SKIP, //ping
                SKIP, //Reset Chat
                register(ServerConfigurationRegistryDataPacket.class, ServerConfigurationRegistryDataPacket.SERIALIZER)
            );
        }
    }

    final class ServerPlay extends Server {
        protected ServerPlay() {
            super(
                ConnectionState.PLAY
            );
        }
    }

    abstract sealed class AbstractRegistry<T> implements PacketRegistry<T> {

        private final ConnectionState state;
        private final ConnectionSide side;
        private final PacketInfo<? extends T>[] packets;

        //TODO: i hate when doing this 
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
                if (e == null || e.type == null || e.codec == null) {
                    packets[id] = null;
                    continue;
                }
            
                packets[id] = new PacketInfo<>(
                    (Class<T>) e.type,
                    id,
                    (StreamCodec<T>) e.codec
                );
            }
        }

//        @SuppressWarnings("unchecked")
//        @SafeVarargs
//        protected AbstractRegistry(
//                ConnectionState state,
//                ConnectionSide side,
//                Entry<? extends T>... entries
//        ) {
//            this.state = state;
//            this.side = side;
//            this.packets = (PacketInfo<? extends T>[]) new PacketInfo[entries.length];
//
//            for (int id = 0; id < entries.length; id++) {
//                Entry<? extends T> e = entries[id];
//                if (e != null) {
//                    packets[id] = new PacketInfo<>(
//                            (Class<T>) e.type,
//                            id,
//                            (StreamCodec<T>) e.codec
//                    );
//                }
//            }
//        }

        @Override
        public T create(int packetId, ByteBuf buf) {
            PacketInfo<? extends T> info = packets[packetId];

            if (info == null) {
                return null;
            }
        
            return cast(info).codec.read(buf);
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
