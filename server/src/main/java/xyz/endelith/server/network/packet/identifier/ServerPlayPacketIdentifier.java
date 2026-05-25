package xyz.endelith.server.network.packet.identifier;

/**
 * A class containing identifiers of play clientbound (server → client) packets.
 *
 * <p>This class is auto-generated. Do not edit manually.
 */
public final class ServerPlayPacketIdentifier {

    /**
     * An identifier of {@code bundle_delimiter} packet.
     */
    public static final int BUNDLE_DELIMITER = 0x00;

    /**
     * An identifier of {@code add_entity} packet.
     */
    public static final int ADD_ENTITY = 0x01;

    /**
     * An identifier of {@code animate} packet.
     */
    public static final int ANIMATE = 0x02;

    /**
     * An identifier of {@code award_stats} packet.
     */
    public static final int AWARD_STATS = 0x03;

    /**
     * An identifier of {@code block_changed_ack} packet.
     */
    public static final int BLOCK_CHANGED_ACK = 0x04;

    /**
     * An identifier of {@code block_destruction} packet.
     */
    public static final int BLOCK_DESTRUCTION = 0x05;

    /**
     * An identifier of {@code block_entity_data} packet.
     */
    public static final int BLOCK_ENTITY_DATA = 0x06;

    /**
     * An identifier of {@code block_event} packet.
     */
    public static final int BLOCK_EVENT = 0x07;

    /**
     * An identifier of {@code block_update} packet.
     */
    public static final int BLOCK_UPDATE = 0x08;

    /**
     * An identifier of {@code boss_event} packet.
     */
    public static final int BOSS_EVENT = 0x09;

    /**
     * An identifier of {@code change_difficulty} packet.
     */
    public static final int CHANGE_DIFFICULTY = 0x0A;

    /**
     * An identifier of {@code chunk_batch_finished} packet.
     */
    public static final int CHUNK_BATCH_FINISHED = 0x0B;

    /**
     * An identifier of {@code chunk_batch_start} packet.
     */
    public static final int CHUNK_BATCH_START = 0x0C;

    /**
     * An identifier of {@code chunks_biomes} packet.
     */
    public static final int CHUNKS_BIOMES = 0x0D;

    /**
     * An identifier of {@code clear_titles} packet.
     */
    public static final int CLEAR_TITLES = 0x0E;

    /**
     * An identifier of {@code command_suggestions} packet.
     */
    public static final int COMMAND_SUGGESTIONS = 0x0F;

    /**
     * An identifier of {@code commands} packet.
     */
    public static final int COMMANDS = 0x10;

    /**
     * An identifier of {@code container_close} packet.
     */
    public static final int CONTAINER_CLOSE = 0x11;

    /**
     * An identifier of {@code container_set_content} packet.
     */
    public static final int CONTAINER_SET_CONTENT = 0x12;

    /**
     * An identifier of {@code container_set_data} packet.
     */
    public static final int CONTAINER_SET_DATA = 0x13;

    /**
     * An identifier of {@code container_set_slot} packet.
     */
    public static final int CONTAINER_SET_SLOT = 0x14;

    /**
     * An identifier of {@code cookie_request} packet.
     */
    public static final int COOKIE_REQUEST = 0x15;

    /**
     * An identifier of {@code cooldown} packet.
     */
    public static final int COOLDOWN = 0x16;

    /**
     * An identifier of {@code custom_chat_completions} packet.
     */
    public static final int CUSTOM_CHAT_COMPLETIONS = 0x17;

    /**
     * An identifier of {@code custom_payload} packet.
     */
    public static final int CUSTOM_PAYLOAD = 0x18;

    /**
     * An identifier of {@code damage_event} packet.
     */
    public static final int DAMAGE_EVENT = 0x19;

    /**
     * An identifier of {@code debug/block_value} packet.
     */
    public static final int DEBUG_BLOCK_VALUE = 0x1A;

    /**
     * An identifier of {@code debug/chunk_value} packet.
     */
    public static final int DEBUG_CHUNK_VALUE = 0x1B;

    /**
     * An identifier of {@code debug/entity_value} packet.
     */
    public static final int DEBUG_ENTITY_VALUE = 0x1C;

    /**
     * An identifier of {@code debug/event} packet.
     */
    public static final int DEBUG_EVENT = 0x1D;

    /**
     * An identifier of {@code debug_sample} packet.
     */
    public static final int DEBUG_SAMPLE = 0x1E;

    /**
     * An identifier of {@code delete_chat} packet.
     */
    public static final int DELETE_CHAT = 0x1F;

    /**
     * An identifier of {@code disconnect} packet.
     */
    public static final int DISCONNECT = 0x20;

    /**
     * An identifier of {@code disguised_chat} packet.
     */
    public static final int DISGUISED_CHAT = 0x21;

    /**
     * An identifier of {@code entity_event} packet.
     */
    public static final int ENTITY_EVENT = 0x22;

    /**
     * An identifier of {@code entity_position_sync} packet.
     */
    public static final int ENTITY_POSITION_SYNC = 0x23;

    /**
     * An identifier of {@code explode} packet.
     */
    public static final int EXPLODE = 0x24;

    /**
     * An identifier of {@code forget_level_chunk} packet.
     */
    public static final int FORGET_LEVEL_CHUNK = 0x25;

    /**
     * An identifier of {@code game_event} packet.
     */
    public static final int GAME_EVENT = 0x26;

    /**
     * An identifier of {@code game_rule_values} packet.
     */
    public static final int GAME_RULE_VALUES = 0x27;

    /**
     * An identifier of {@code game_test_highlight_pos} packet.
     */
    public static final int GAME_TEST_HIGHLIGHT_POS = 0x28;

    /**
     * An identifier of {@code mount_screen_open} packet.
     */
    public static final int MOUNT_SCREEN_OPEN = 0x29;

    /**
     * An identifier of {@code hurt_animation} packet.
     */
    public static final int HURT_ANIMATION = 0x2A;

    /**
     * An identifier of {@code initialize_border} packet.
     */
    public static final int INITIALIZE_BORDER = 0x2B;

    /**
     * An identifier of {@code keep_alive} packet.
     */
    public static final int KEEP_ALIVE = 0x2C;

    /**
     * An identifier of {@code level_chunk_with_light} packet.
     */
    public static final int LEVEL_CHUNK_WITH_LIGHT = 0x2D;

    /**
     * An identifier of {@code level_event} packet.
     */
    public static final int LEVEL_EVENT = 0x2E;

    /**
     * An identifier of {@code level_particles} packet.
     */
    public static final int LEVEL_PARTICLES = 0x2F;

    /**
     * An identifier of {@code light_update} packet.
     */
    public static final int LIGHT_UPDATE = 0x30;

    /**
     * An identifier of {@code login} packet.
     */
    public static final int LOGIN = 0x31;

    /**
     * An identifier of {@code low_disk_space_warning} packet.
     */
    public static final int LOW_DISK_SPACE_WARNING = 0x32;

    /**
     * An identifier of {@code map_item_data} packet.
     */
    public static final int MAP_ITEM_DATA = 0x33;

    /**
     * An identifier of {@code merchant_offers} packet.
     */
    public static final int MERCHANT_OFFERS = 0x34;

    /**
     * An identifier of {@code move_entity_pos} packet.
     */
    public static final int MOVE_ENTITY_POS = 0x35;

    /**
     * An identifier of {@code move_entity_pos_rot} packet.
     */
    public static final int MOVE_ENTITY_POS_ROT = 0x36;

    /**
     * An identifier of {@code move_minecart_along_track} packet.
     */
    public static final int MOVE_MINECART_ALONG_TRACK = 0x37;

    /**
     * An identifier of {@code move_entity_rot} packet.
     */
    public static final int MOVE_ENTITY_ROT = 0x38;

    /**
     * An identifier of {@code move_vehicle} packet.
     */
    public static final int MOVE_VEHICLE = 0x39;

    /**
     * An identifier of {@code open_book} packet.
     */
    public static final int OPEN_BOOK = 0x3A;

    /**
     * An identifier of {@code open_screen} packet.
     */
    public static final int OPEN_SCREEN = 0x3B;

    /**
     * An identifier of {@code open_sign_editor} packet.
     */
    public static final int OPEN_SIGN_EDITOR = 0x3C;

    /**
     * An identifier of {@code ping} packet.
     */
    public static final int PING = 0x3D;

    /**
     * An identifier of {@code pong_response} packet.
     */
    public static final int PONG_RESPONSE = 0x3E;

    /**
     * An identifier of {@code place_ghost_recipe} packet.
     */
    public static final int PLACE_GHOST_RECIPE = 0x3F;

    /**
     * An identifier of {@code player_abilities} packet.
     */
    public static final int PLAYER_ABILITIES = 0x40;

    /**
     * An identifier of {@code player_chat} packet.
     */
    public static final int PLAYER_CHAT = 0x41;

    /**
     * An identifier of {@code player_combat_end} packet.
     */
    public static final int PLAYER_COMBAT_END = 0x42;

    /**
     * An identifier of {@code player_combat_enter} packet.
     */
    public static final int PLAYER_COMBAT_ENTER = 0x43;

    /**
     * An identifier of {@code player_combat_kill} packet.
     */
    public static final int PLAYER_COMBAT_KILL = 0x44;

    /**
     * An identifier of {@code player_info_remove} packet.
     */
    public static final int PLAYER_INFO_REMOVE = 0x45;

    /**
     * An identifier of {@code player_info_update} packet.
     */
    public static final int PLAYER_INFO_UPDATE = 0x46;

    /**
     * An identifier of {@code player_look_at} packet.
     */
    public static final int PLAYER_LOOK_AT = 0x47;

    /**
     * An identifier of {@code player_position} packet.
     */
    public static final int PLAYER_POSITION = 0x48;

    /**
     * An identifier of {@code player_rotation} packet.
     */
    public static final int PLAYER_ROTATION = 0x49;

    /**
     * An identifier of {@code recipe_book_add} packet.
     */
    public static final int RECIPE_BOOK_ADD = 0x4A;

    /**
     * An identifier of {@code recipe_book_remove} packet.
     */
    public static final int RECIPE_BOOK_REMOVE = 0x4B;

    /**
     * An identifier of {@code recipe_book_settings} packet.
     */
    public static final int RECIPE_BOOK_SETTINGS = 0x4C;

    /**
     * An identifier of {@code remove_entities} packet.
     */
    public static final int REMOVE_ENTITIES = 0x4D;

    /**
     * An identifier of {@code remove_mob_effect} packet.
     */
    public static final int REMOVE_MOB_EFFECT = 0x4E;

    /**
     * An identifier of {@code reset_score} packet.
     */
    public static final int RESET_SCORE = 0x4F;

    /**
     * An identifier of {@code resource_pack_pop} packet.
     */
    public static final int RESOURCE_PACK_POP = 0x50;

    /**
     * An identifier of {@code resource_pack_push} packet.
     */
    public static final int RESOURCE_PACK_PUSH = 0x51;

    /**
     * An identifier of {@code respawn} packet.
     */
    public static final int RESPAWN = 0x52;

    /**
     * An identifier of {@code rotate_head} packet.
     */
    public static final int ROTATE_HEAD = 0x53;

    /**
     * An identifier of {@code section_blocks_update} packet.
     */
    public static final int SECTION_BLOCKS_UPDATE = 0x54;

    /**
     * An identifier of {@code select_advancements_tab} packet.
     */
    public static final int SELECT_ADVANCEMENTS_TAB = 0x55;

    /**
     * An identifier of {@code server_data} packet.
     */
    public static final int SERVER_DATA = 0x56;

    /**
     * An identifier of {@code set_action_bar_text} packet.
     */
    public static final int SET_ACTION_BAR_TEXT = 0x57;

    /**
     * An identifier of {@code set_border_center} packet.
     */
    public static final int SET_BORDER_CENTER = 0x58;

    /**
     * An identifier of {@code set_border_lerp_size} packet.
     */
    public static final int SET_BORDER_LERP_SIZE = 0x59;

    /**
     * An identifier of {@code set_border_size} packet.
     */
    public static final int SET_BORDER_SIZE = 0x5A;

    /**
     * An identifier of {@code set_border_warning_delay} packet.
     */
    public static final int SET_BORDER_WARNING_DELAY = 0x5B;

    /**
     * An identifier of {@code set_border_warning_distance} packet.
     */
    public static final int SET_BORDER_WARNING_DISTANCE = 0x5C;

    /**
     * An identifier of {@code set_camera} packet.
     */
    public static final int SET_CAMERA = 0x5D;

    /**
     * An identifier of {@code set_chunk_cache_center} packet.
     */
    public static final int SET_CHUNK_CACHE_CENTER = 0x5E;

    /**
     * An identifier of {@code set_chunk_cache_radius} packet.
     */
    public static final int SET_CHUNK_CACHE_RADIUS = 0x5F;

    /**
     * An identifier of {@code set_cursor_item} packet.
     */
    public static final int SET_CURSOR_ITEM = 0x60;

    /**
     * An identifier of {@code set_default_spawn_position} packet.
     */
    public static final int SET_DEFAULT_SPAWN_POSITION = 0x61;

    /**
     * An identifier of {@code set_display_objective} packet.
     */
    public static final int SET_DISPLAY_OBJECTIVE = 0x62;

    /**
     * An identifier of {@code set_entity_data} packet.
     */
    public static final int SET_ENTITY_DATA = 0x63;

    /**
     * An identifier of {@code set_entity_link} packet.
     */
    public static final int SET_ENTITY_LINK = 0x64;

    /**
     * An identifier of {@code set_entity_motion} packet.
     */
    public static final int SET_ENTITY_MOTION = 0x65;

    /**
     * An identifier of {@code set_equipment} packet.
     */
    public static final int SET_EQUIPMENT = 0x66;

    /**
     * An identifier of {@code set_experience} packet.
     */
    public static final int SET_EXPERIENCE = 0x67;

    /**
     * An identifier of {@code set_health} packet.
     */
    public static final int SET_HEALTH = 0x68;

    /**
     * An identifier of {@code set_held_slot} packet.
     */
    public static final int SET_HELD_SLOT = 0x69;

    /**
     * An identifier of {@code set_objective} packet.
     */
    public static final int SET_OBJECTIVE = 0x6A;

    /**
     * An identifier of {@code set_passengers} packet.
     */
    public static final int SET_PASSENGERS = 0x6B;

    /**
     * An identifier of {@code set_player_inventory} packet.
     */
    public static final int SET_PLAYER_INVENTORY = 0x6C;

    /**
     * An identifier of {@code set_player_team} packet.
     */
    public static final int SET_PLAYER_TEAM = 0x6D;

    /**
     * An identifier of {@code set_score} packet.
     */
    public static final int SET_SCORE = 0x6E;

    /**
     * An identifier of {@code set_simulation_distance} packet.
     */
    public static final int SET_SIMULATION_DISTANCE = 0x6F;

    /**
     * An identifier of {@code set_subtitle_text} packet.
     */
    public static final int SET_SUBTITLE_TEXT = 0x70;

    /**
     * An identifier of {@code set_time} packet.
     */
    public static final int SET_TIME = 0x71;

    /**
     * An identifier of {@code set_title_text} packet.
     */
    public static final int SET_TITLE_TEXT = 0x72;

    /**
     * An identifier of {@code set_titles_animation} packet.
     */
    public static final int SET_TITLES_ANIMATION = 0x73;

    /**
     * An identifier of {@code sound_entity} packet.
     */
    public static final int SOUND_ENTITY = 0x74;

    /**
     * An identifier of {@code sound} packet.
     */
    public static final int SOUND = 0x75;

    /**
     * An identifier of {@code start_configuration} packet.
     */
    public static final int START_CONFIGURATION = 0x76;

    /**
     * An identifier of {@code stop_sound} packet.
     */
    public static final int STOP_SOUND = 0x77;

    /**
     * An identifier of {@code store_cookie} packet.
     */
    public static final int STORE_COOKIE = 0x78;

    /**
     * An identifier of {@code system_chat} packet.
     */
    public static final int SYSTEM_CHAT = 0x79;

    /**
     * An identifier of {@code tab_list} packet.
     */
    public static final int TAB_LIST = 0x7A;

    /**
     * An identifier of {@code tag_query} packet.
     */
    public static final int TAG_QUERY = 0x7B;

    /**
     * An identifier of {@code take_item_entity} packet.
     */
    public static final int TAKE_ITEM_ENTITY = 0x7C;

    /**
     * An identifier of {@code teleport_entity} packet.
     */
    public static final int TELEPORT_ENTITY = 0x7D;

    /**
     * An identifier of {@code test_instance_block_status} packet.
     */
    public static final int TEST_INSTANCE_BLOCK_STATUS = 0x7E;

    /**
     * An identifier of {@code ticking_state} packet.
     */
    public static final int TICKING_STATE = 0x7F;

    /**
     * An identifier of {@code ticking_step} packet.
     */
    public static final int TICKING_STEP = 0x80;

    /**
     * An identifier of {@code transfer} packet.
     */
    public static final int TRANSFER = 0x81;

    /**
     * An identifier of {@code update_advancements} packet.
     */
    public static final int UPDATE_ADVANCEMENTS = 0x82;

    /**
     * An identifier of {@code update_attributes} packet.
     */
    public static final int UPDATE_ATTRIBUTES = 0x83;

    /**
     * An identifier of {@code update_mob_effect} packet.
     */
    public static final int UPDATE_MOB_EFFECT = 0x84;

    /**
     * An identifier of {@code update_recipes} packet.
     */
    public static final int UPDATE_RECIPES = 0x85;

    /**
     * An identifier of {@code update_tags} packet.
     */
    public static final int UPDATE_TAGS = 0x86;

    /**
     * An identifier of {@code projectile_power} packet.
     */
    public static final int PROJECTILE_POWER = 0x87;

    /**
     * An identifier of {@code custom_report_details} packet.
     */
    public static final int CUSTOM_REPORT_DETAILS = 0x88;

    /**
     * An identifier of {@code server_links} packet.
     */
    public static final int SERVER_LINKS = 0x89;

    /**
     * An identifier of {@code waypoint} packet.
     */
    public static final int WAYPOINT = 0x8A;

    /**
     * An identifier of {@code clear_dialog} packet.
     */
    public static final int CLEAR_DIALOG = 0x8B;

    /**
     * An identifier of {@code show_dialog} packet.
     */
    public static final int SHOW_DIALOG = 0x8C;

    private ServerPlayPacketIdentifier() {
    }
}
