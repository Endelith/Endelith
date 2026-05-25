package xyz.endelith.server.network.packet.identifier;

/**
 * A class containing identifiers of configuration clientbound (server → client) packets.
 *
 * <p>This class is auto-generated. Do not edit manually.
 */
public final class ServerConfigurationPacketIdentifier {

    /**
     * An identifier of {@code cookie_request} packet.
     */
    public static final int COOKIE_REQUEST = 0x00;

    /**
     * An identifier of {@code custom_payload} packet.
     */
    public static final int CUSTOM_PAYLOAD = 0x01;

    /**
     * An identifier of {@code disconnect} packet.
     */
    public static final int DISCONNECT = 0x02;

    /**
     * An identifier of {@code finish_configuration} packet.
     */
    public static final int FINISH_CONFIGURATION = 0x03;

    /**
     * An identifier of {@code keep_alive} packet.
     */
    public static final int KEEP_ALIVE = 0x04;

    /**
     * An identifier of {@code ping} packet.
     */
    public static final int PING = 0x05;

    /**
     * An identifier of {@code reset_chat} packet.
     */
    public static final int RESET_CHAT = 0x06;

    /**
     * An identifier of {@code registry_data} packet.
     */
    public static final int REGISTRY_DATA = 0x07;

    /**
     * An identifier of {@code resource_pack_pop} packet.
     */
    public static final int RESOURCE_PACK_POP = 0x08;

    /**
     * An identifier of {@code resource_pack_push} packet.
     */
    public static final int RESOURCE_PACK_PUSH = 0x09;

    /**
     * An identifier of {@code store_cookie} packet.
     */
    public static final int STORE_COOKIE = 0x0A;

    /**
     * An identifier of {@code transfer} packet.
     */
    public static final int TRANSFER = 0x0B;

    /**
     * An identifier of {@code update_enabled_features} packet.
     */
    public static final int UPDATE_ENABLED_FEATURES = 0x0C;

    /**
     * An identifier of {@code update_tags} packet.
     */
    public static final int UPDATE_TAGS = 0x0D;

    /**
     * An identifier of {@code select_known_packs} packet.
     */
    public static final int SELECT_KNOWN_PACKS = 0x0E;

    /**
     * An identifier of {@code custom_report_details} packet.
     */
    public static final int CUSTOM_REPORT_DETAILS = 0x0F;

    /**
     * An identifier of {@code server_links} packet.
     */
    public static final int SERVER_LINKS = 0x10;

    /**
     * An identifier of {@code clear_dialog} packet.
     */
    public static final int CLEAR_DIALOG = 0x11;

    /**
     * An identifier of {@code show_dialog} packet.
     */
    public static final int SHOW_DIALOG = 0x12;

    /**
     * An identifier of {@code code_of_conduct} packet.
     */
    public static final int CODE_OF_CONDUCT = 0x13;

    private ServerConfigurationPacketIdentifier() {
    }
}
