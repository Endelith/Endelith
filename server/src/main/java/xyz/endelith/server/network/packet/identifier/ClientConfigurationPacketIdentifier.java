package xyz.endelith.server.network.packet.identifier;

/**
 * A class containing identifiers of configuration serverbound (client → server) packets.
 *
 * <p>This class is auto-generated. Do not edit manually.
 */
public final class ClientConfigurationPacketIdentifier {

    /**
     * An identifier of {@code client_information} packet.
     */
    public static final int CLIENT_INFORMATION = 0x00;

    /**
     * An identifier of {@code cookie_response} packet.
     */
    public static final int COOKIE_RESPONSE = 0x01;

    /**
     * An identifier of {@code custom_payload} packet.
     */
    public static final int CUSTOM_PAYLOAD = 0x02;

    /**
     * An identifier of {@code finish_configuration} packet.
     */
    public static final int FINISH_CONFIGURATION = 0x03;

    /**
     * An identifier of {@code keep_alive} packet.
     */
    public static final int KEEP_ALIVE = 0x04;

    /**
     * An identifier of {@code pong} packet.
     */
    public static final int PONG = 0x05;

    /**
     * An identifier of {@code resource_pack} packet.
     */
    public static final int RESOURCE_PACK = 0x06;

    /**
     * An identifier of {@code select_known_packs} packet.
     */
    public static final int SELECT_KNOWN_PACKS = 0x07;

    /**
     * An identifier of {@code custom_click_action} packet.
     */
    public static final int CUSTOM_CLICK_ACTION = 0x08;

    /**
     * An identifier of {@code accept_code_of_conduct} packet.
     */
    public static final int ACCEPT_CODE_OF_CONDUCT = 0x09;

    private ClientConfigurationPacketIdentifier() {
    }
}
