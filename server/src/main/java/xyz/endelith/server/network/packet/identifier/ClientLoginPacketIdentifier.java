package xyz.endelith.server.network.packet.identifier;

/**
 * A class containing identifiers of login serverbound (client → server) packets.
 *
 * <p>This class is auto-generated. Do not edit manually.
 */
public final class ClientLoginPacketIdentifier {

    /**
     * An identifier of {@code hello} packet.
     */
    public static final int HELLO = 0x00;

    /**
     * An identifier of {@code key} packet.
     */
    public static final int KEY = 0x01;

    /**
     * An identifier of {@code custom_query_answer} packet.
     */
    public static final int CUSTOM_QUERY_ANSWER = 0x02;

    /**
     * An identifier of {@code login_acknowledged} packet.
     */
    public static final int LOGIN_ACKNOWLEDGED = 0x03;

    /**
     * An identifier of {@code cookie_response} packet.
     */
    public static final int COOKIE_RESPONSE = 0x04;

    private ClientLoginPacketIdentifier() {
    }
}
