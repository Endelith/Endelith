package xyz.endelith.server.network.packet;

import java.util.Objects;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.endelith.cosine.stream.StreamCodec;
import xyz.endelith.server.network.ConnectionState;
import xyz.endelith.server.network.packet.client.handshake.ClientHandshakeIntentionPacket;
import xyz.endelith.server.network.packet.client.status.ClientStatusPingRequestPacket;
import xyz.endelith.server.network.packet.client.status.ClientStatusRequestPacket;
import xyz.endelith.server.network.packet.identifier.ClientHandshakePacketIdentifier;
import xyz.endelith.server.network.packet.identifier.ClientStatusPacketIdentifier;
import xyz.endelith.server.network.packet.identifier.ServerLoginPacketIdentifier;
import xyz.endelith.server.network.packet.identifier.ServerStatusPacketIdentifier;
import xyz.endelith.server.network.packet.server.login.ServerLoginDisconnectPacket;
import xyz.endelith.server.network.packet.server.status.ServerStatusPongResponsePacket;
import xyz.endelith.server.network.packet.server.status.ServerStatusResponsePacket;

public final class PacketRegistry {

    private static final Logger LOGGER = LoggerFactory.getLogger(PacketRegistry.class);

    private final PacketEntry<?>[][][] entries;

    public PacketRegistry() {
        this.entries = new PacketMapBuilder()
            .state(ConnectionState.HANDSHAKE)
                .serverbound(
                    ClientHandshakePacketIdentifier.INTENTION,
                    ClientHandshakeIntentionPacket.class,
                    ClientHandshakeIntentionPacket.STREAM_CODEC
                )
            .state(ConnectionState.STATUS)
                .serverbound(
                    ClientStatusPacketIdentifier.STATUS_REQUEST,
                    ClientStatusRequestPacket.class,
                    ClientStatusRequestPacket.STREAM_CODEC
                )
                .serverbound(
                    ClientStatusPacketIdentifier.PING_REQUEST,
                    ClientStatusPingRequestPacket.class,
                    ClientStatusPingRequestPacket.STREAM_CODEC
                )
                .clientbound(
                    ServerStatusPacketIdentifier.STATUS_RESPONSE,
                    ServerStatusResponsePacket.class,
                    ServerStatusResponsePacket.STREAM_CODEC
                )
                .clientbound(
                    ServerStatusPacketIdentifier.PONG_RESPONSE,
                    ServerStatusPongResponsePacket.class,
                    ServerStatusPongResponsePacket.STREAM_CODEC
                )
            .state(ConnectionState.LOGIN)
                .clientbound(
                    ServerLoginPacketIdentifier.LOGIN_DISCONNECT,
                    ServerLoginDisconnectPacket.class,
                    ServerLoginDisconnectPacket.STREAM_CODEC
                )
            .build();
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public <P> PacketEntry<P> byId(ConnectionState state, ConnectionSide side, int id) {
        PacketEntry<?> entry = this.entries[state.ordinal()][side.ordinal()][id];
        if (entry == null) {
            LOGGER.warn("Unknown packet: state={} side={} id=0x{}", state, side, String.format("%02X", id));
            return null;
        }
        return (PacketEntry<P>) entry;
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public <P> PacketEntry<P> byClass(ConnectionState state, ConnectionSide side, Class<?> packetClass) {
        for (PacketEntry<?> entry : this.entries[state.ordinal()][side.ordinal()]) {
            if (entry != null && entry.packetClass() == packetClass) {
                return (PacketEntry<P>) entry;
            }
        }
        LOGGER.warn("Unknown packet class: state={} side={} class={}", state, side, packetClass.getName());
        return null;
    }

    public record PacketEntry<P>(
            int id,
            ConnectionState state,
            ConnectionSide side,
            Class<P> packetClass,
            StreamCodec<P> streamCodec
    ) {
        public PacketEntry {
            Objects.requireNonNull(state, "state");
            Objects.requireNonNull(side, "side");
            Objects.requireNonNull(packetClass, "packet class");
            Objects.requireNonNull(streamCodec, "stream codec");
        }
    }

    private static final class PacketMapBuilder {

        private static final int MAX_PACKET_ID = 256;

        private final PacketEntry<?>[][][] entries =
                new PacketEntry[ConnectionState.values().length][ConnectionSide.values().length][MAX_PACKET_ID];

        public StateBuilder state(ConnectionState state) {
            Objects.requireNonNull(state, "state");
            return new StateBuilder(this, state);
        }

        private <P> void put(
                ConnectionState state,
                ConnectionSide side,
                int id,
                Class<P> packetClass,
                StreamCodec<P> codec
        ) {
            PacketEntry<?> existing = this.entries[state.ordinal()][side.ordinal()][id];
            if (existing != null) {
                throw new IllegalArgumentException(String.format(
                    "Packet already registered: state=%s side=%s id=0x%02X", state, side, id
                ));
            }

            this.entries[state.ordinal()][side.ordinal()][id] =
                    new PacketEntry<>(id, state, side, packetClass, codec);
        }

        public PacketEntry<?>[][][] build() {
            return this.entries;
        }

        public final class StateBuilder {

            private final PacketMapBuilder parent;
            private final ConnectionState state;

            private StateBuilder(PacketMapBuilder parent, ConnectionState state) {
                this.parent = parent;
                this.state = state;
            }

            public <P> StateBuilder serverbound(int id, Class<P> packetClass, StreamCodec<P> codec) {
                this.parent.put(this.state, ConnectionSide.SERVERBOUND, id, packetClass, codec);
                return this;
            }

            public <P> StateBuilder clientbound(int id, Class<P> packetClass, StreamCodec<P> codec) {
                this.parent.put(this.state, ConnectionSide.CLIENTBOUND, id, packetClass, codec);
                return this;
            }

            public StateBuilder state(ConnectionState nextState) {
                return this.parent.state(nextState);
            }

            public PacketEntry<?>[][][] build() {
                return this.parent.build();
            }
        }
    }

    public enum ConnectionSide {
        CLIENTBOUND,
        SERVERBOUND
    }
}
