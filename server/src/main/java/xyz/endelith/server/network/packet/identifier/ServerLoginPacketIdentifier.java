package xyz.endelith.server.network.packet.identifier;

/**
 * A class containing identifiers of login clientbound (server → client) packets.
 *
 * <p>This class is auto-generated. Do not edit manually.
 */
public final class ServerLoginPacketIdentifier {

    /**
     * An identifier of {@code login_disconnect} packet.
     */
    public static final int LOGIN_DISCONNECT = 0x00;

    /**
     * An identifier of {@code hello} packet.
     */
    public static final int HELLO = 0x01;

    /**
     * An identifier of {@code login_finished} packet.
     */
    public static final int LOGIN_FINISHED = 0x02;

    /**
     * An identifier of {@code login_compression} packet.
     */
    public static final int LOGIN_COMPRESSION = 0x03;

    /**
     * An identifier of {@code custom_query} packet.
     */
    public static final int CUSTOM_QUERY = 0x04;

    /**
     * An identifier of {@code cookie_request} packet.
     */
    public static final int COOKIE_REQUEST = 0x05;

    private ServerLoginPacketIdentifier() {
    }
}
