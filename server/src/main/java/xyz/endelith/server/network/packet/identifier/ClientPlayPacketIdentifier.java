package xyz.endelith.server.network.packet.identifier;

/**
 * A class containing identifiers of play serverbound (client → server) packets.
 *
 * <p>This class is auto-generated. Do not edit manually.
 */
public final class ClientPlayPacketIdentifier {

    /**
     * An identifier of {@code accept_teleportation} packet.
     */
    public static final int ACCEPT_TELEPORTATION = 0x00;

    /**
     * An identifier of {@code attack} packet.
     */
    public static final int ATTACK = 0x01;

    /**
     * An identifier of {@code block_entity_tag_query} packet.
     */
    public static final int BLOCK_ENTITY_TAG_QUERY = 0x02;

    /**
     * An identifier of {@code bundle_item_selected} packet.
     */
    public static final int BUNDLE_ITEM_SELECTED = 0x03;

    /**
     * An identifier of {@code change_difficulty} packet.
     */
    public static final int CHANGE_DIFFICULTY = 0x04;

    /**
     * An identifier of {@code change_game_mode} packet.
     */
    public static final int CHANGE_GAME_MODE = 0x05;

    /**
     * An identifier of {@code chat_ack} packet.
     */
    public static final int CHAT_ACK = 0x06;

    /**
     * An identifier of {@code chat_command} packet.
     */
    public static final int CHAT_COMMAND = 0x07;

    /**
     * An identifier of {@code chat_command_signed} packet.
     */
    public static final int CHAT_COMMAND_SIGNED = 0x08;

    /**
     * An identifier of {@code chat} packet.
     */
    public static final int CHAT = 0x09;

    /**
     * An identifier of {@code chat_session_update} packet.
     */
    public static final int CHAT_SESSION_UPDATE = 0x0A;

    /**
     * An identifier of {@code chunk_batch_received} packet.
     */
    public static final int CHUNK_BATCH_RECEIVED = 0x0B;

    /**
     * An identifier of {@code client_command} packet.
     */
    public static final int CLIENT_COMMAND = 0x0C;

    /**
     * An identifier of {@code client_tick_end} packet.
     */
    public static final int CLIENT_TICK_END = 0x0D;

    /**
     * An identifier of {@code client_information} packet.
     */
    public static final int CLIENT_INFORMATION = 0x0E;

    /**
     * An identifier of {@code command_suggestion} packet.
     */
    public static final int COMMAND_SUGGESTION = 0x0F;

    /**
     * An identifier of {@code configuration_acknowledged} packet.
     */
    public static final int CONFIGURATION_ACKNOWLEDGED = 0x10;

    /**
     * An identifier of {@code container_button_click} packet.
     */
    public static final int CONTAINER_BUTTON_CLICK = 0x11;

    /**
     * An identifier of {@code container_click} packet.
     */
    public static final int CONTAINER_CLICK = 0x12;

    /**
     * An identifier of {@code container_close} packet.
     */
    public static final int CONTAINER_CLOSE = 0x13;

    /**
     * An identifier of {@code container_slot_state_changed} packet.
     */
    public static final int CONTAINER_SLOT_STATE_CHANGED = 0x14;

    /**
     * An identifier of {@code cookie_response} packet.
     */
    public static final int COOKIE_RESPONSE = 0x15;

    /**
     * An identifier of {@code custom_payload} packet.
     */
    public static final int CUSTOM_PAYLOAD = 0x16;

    /**
     * An identifier of {@code debug_subscription_request} packet.
     */
    public static final int DEBUG_SUBSCRIPTION_REQUEST = 0x17;

    /**
     * An identifier of {@code edit_book} packet.
     */
    public static final int EDIT_BOOK = 0x18;

    /**
     * An identifier of {@code entity_tag_query} packet.
     */
    public static final int ENTITY_TAG_QUERY = 0x19;

    /**
     * An identifier of {@code interact} packet.
     */
    public static final int INTERACT = 0x1A;

    /**
     * An identifier of {@code jigsaw_generate} packet.
     */
    public static final int JIGSAW_GENERATE = 0x1B;

    /**
     * An identifier of {@code keep_alive} packet.
     */
    public static final int KEEP_ALIVE = 0x1C;

    /**
     * An identifier of {@code lock_difficulty} packet.
     */
    public static final int LOCK_DIFFICULTY = 0x1D;

    /**
     * An identifier of {@code move_player_pos} packet.
     */
    public static final int MOVE_PLAYER_POS = 0x1E;

    /**
     * An identifier of {@code move_player_pos_rot} packet.
     */
    public static final int MOVE_PLAYER_POS_ROT = 0x1F;

    /**
     * An identifier of {@code move_player_rot} packet.
     */
    public static final int MOVE_PLAYER_ROT = 0x20;

    /**
     * An identifier of {@code move_player_status_only} packet.
     */
    public static final int MOVE_PLAYER_STATUS_ONLY = 0x21;

    /**
     * An identifier of {@code move_vehicle} packet.
     */
    public static final int MOVE_VEHICLE = 0x22;

    /**
     * An identifier of {@code paddle_boat} packet.
     */
    public static final int PADDLE_BOAT = 0x23;

    /**
     * An identifier of {@code pick_item_from_block} packet.
     */
    public static final int PICK_ITEM_FROM_BLOCK = 0x24;

    /**
     * An identifier of {@code pick_item_from_entity} packet.
     */
    public static final int PICK_ITEM_FROM_ENTITY = 0x25;

    /**
     * An identifier of {@code ping_request} packet.
     */
    public static final int PING_REQUEST = 0x26;

    /**
     * An identifier of {@code place_recipe} packet.
     */
    public static final int PLACE_RECIPE = 0x27;

    /**
     * An identifier of {@code player_abilities} packet.
     */
    public static final int PLAYER_ABILITIES = 0x28;

    /**
     * An identifier of {@code player_action} packet.
     */
    public static final int PLAYER_ACTION = 0x29;

    /**
     * An identifier of {@code player_command} packet.
     */
    public static final int PLAYER_COMMAND = 0x2A;

    /**
     * An identifier of {@code player_input} packet.
     */
    public static final int PLAYER_INPUT = 0x2B;

    /**
     * An identifier of {@code player_loaded} packet.
     */
    public static final int PLAYER_LOADED = 0x2C;

    /**
     * An identifier of {@code pong} packet.
     */
    public static final int PONG = 0x2D;

    /**
     * An identifier of {@code recipe_book_change_settings} packet.
     */
    public static final int RECIPE_BOOK_CHANGE_SETTINGS = 0x2E;

    /**
     * An identifier of {@code recipe_book_seen_recipe} packet.
     */
    public static final int RECIPE_BOOK_SEEN_RECIPE = 0x2F;

    /**
     * An identifier of {@code rename_item} packet.
     */
    public static final int RENAME_ITEM = 0x30;

    /**
     * An identifier of {@code resource_pack} packet.
     */
    public static final int RESOURCE_PACK = 0x31;

    /**
     * An identifier of {@code seen_advancements} packet.
     */
    public static final int SEEN_ADVANCEMENTS = 0x32;

    /**
     * An identifier of {@code select_trade} packet.
     */
    public static final int SELECT_TRADE = 0x33;

    /**
     * An identifier of {@code set_beacon} packet.
     */
    public static final int SET_BEACON = 0x34;

    /**
     * An identifier of {@code set_carried_item} packet.
     */
    public static final int SET_CARRIED_ITEM = 0x35;

    /**
     * An identifier of {@code set_command_block} packet.
     */
    public static final int SET_COMMAND_BLOCK = 0x36;

    /**
     * An identifier of {@code set_command_minecart} packet.
     */
    public static final int SET_COMMAND_MINECART = 0x37;

    /**
     * An identifier of {@code set_creative_mode_slot} packet.
     */
    public static final int SET_CREATIVE_MODE_SLOT = 0x38;

    /**
     * An identifier of {@code set_game_rule} packet.
     */
    public static final int SET_GAME_RULE = 0x39;

    /**
     * An identifier of {@code set_jigsaw_block} packet.
     */
    public static final int SET_JIGSAW_BLOCK = 0x3A;

    /**
     * An identifier of {@code set_structure_block} packet.
     */
    public static final int SET_STRUCTURE_BLOCK = 0x3B;

    /**
     * An identifier of {@code set_test_block} packet.
     */
    public static final int SET_TEST_BLOCK = 0x3C;

    /**
     * An identifier of {@code sign_update} packet.
     */
    public static final int SIGN_UPDATE = 0x3D;

    /**
     * An identifier of {@code spectate_entity} packet.
     */
    public static final int SPECTATE_ENTITY = 0x3E;

    /**
     * An identifier of {@code swing} packet.
     */
    public static final int SWING = 0x3F;

    /**
     * An identifier of {@code teleport_to_entity} packet.
     */
    public static final int TELEPORT_TO_ENTITY = 0x40;

    /**
     * An identifier of {@code test_instance_block_action} packet.
     */
    public static final int TEST_INSTANCE_BLOCK_ACTION = 0x41;

    /**
     * An identifier of {@code use_item_on} packet.
     */
    public static final int USE_ITEM_ON = 0x42;

    /**
     * An identifier of {@code use_item} packet.
     */
    public static final int USE_ITEM = 0x43;

    /**
     * An identifier of {@code custom_click_action} packet.
     */
    public static final int CUSTOM_CLICK_ACTION = 0x44;

    private ClientPlayPacketIdentifier() {
    }
}
