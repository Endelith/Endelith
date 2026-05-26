package xyz.endelith.server.registry.blockstate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import net.kyori.adventure.key.Key;
import xyz.endelith.cosine.codec.Codec;
import xyz.endelith.cosine.codec.StructCodec;
import xyz.endelith.registry.blockstate.BlockStateRegistry;
import xyz.endelith.registry.holder.Holder;
import xyz.endelith.registry.holder.Holder.Reference;
import xyz.endelith.server.registry.MinecraftRegistryImpl.RegistrationInfo;
import xyz.endelith.server.util.data.DataUtil;
import xyz.endelith.world.block.BlockState;
import xyz.endelith.world.block.BlockType;

public final class BlockStateRegistryImpl implements BlockStateRegistry {

    private static final String BLOCK_STATES_RESOURCE = "registries/block_states.json";
    private static final String BLOCKS_RESOURCE = "registries/blocks.json";

    private static final Codec<Map<String, String>> PROPERTIES_CODEC = Codec.STRING
            .mapValue(Codec.STRING)
            .defaultValue(Map.of());

    private static final StructCodec<BlockStateData> BLOCK_STATE_CODEC = StructCodec.of(
            "properties", PROPERTIES_CODEC, BlockStateData::properties,
            "air", Codec.BOOLEAN.defaultValue(false), BlockStateData::air,
            "has_fluid_state", Codec.BOOLEAN.defaultValue(false), BlockStateData::hasFluidState,
            "blocks_motion", Codec.BOOLEAN.defaultValue(true), BlockStateData::blocksMotion,
            "leaves", Codec.BOOLEAN.defaultValue(false), BlockStateData::leaves,
            BlockStateData::new
    );

    private static final StructCodec<BlockData> BLOCK_CODEC = StructCodec.of(
            "required_feature_flags", Codec.KEY.list(), BlockData::requiredFeatureFlags,
            "default_state", Codec.INT, BlockData::defaultState,
            "states", Codec.INT.list(), BlockData::states,
            BlockData::new
    );

    private final List<RegistryBlockState> blockStates;
    private final Map<RegistryBlockState, Integer> blockStateToIndex;

    private final Map<Key, Map<Map<String, String>, RegistryBlockState>> possibleStates;
    private final Map<Key, RegistryBlockState> defaultStates;

    public BlockStateRegistryImpl() {
        final List<RegistrationInfo<BlockStateData>> stateRegistrations = DataUtil
                .loadEntries(BLOCK_STATES_RESOURCE, BLOCK_STATE_CODEC, data -> data)
                .registrations();
        final List<RegistrationInfo<BlockData>> blockRegistrations = DataUtil
                .loadEntries(BLOCKS_RESOURCE, BLOCK_CODEC, data -> data)
                .registrations();

        List<RegistryBlockState> blockStates = new ArrayList<>();
        Map<RegistryBlockState, Integer> blockStateToIndex = new HashMap<>();

        for (int index = 0; index < stateRegistrations.size(); index++) {
            RegistrationInfo<BlockStateData> registration = stateRegistrations.get(index);
            BlockStateData data = registration.value();
            RegistryBlockState state = new RegistryBlockState(
                    new Holder.Reference<>(registration.key()),
                    data.properties(),
                    data.air(),
                    data.hasFluidState(),
                    data.blocksMotion(),
                    data.leaves()
            );

            blockStates.add(state);
            blockStateToIndex.put(state, index);
        }

        this.blockStates = List.copyOf(blockStates);
        this.blockStateToIndex = Map.copyOf(blockStateToIndex);

        Map<Key, Map<Map<String, String>, RegistryBlockState>> possibleStates = new HashMap<>();
        Map<Key, RegistryBlockState> defaultStates = new HashMap<>();

        for (RegistrationInfo<BlockData> registration : blockRegistrations) {
            Key blockTypeKey = registration.key();
            BlockData data = registration.value();

            RegistryBlockState defaultState = this.byIndex(data.defaultState());
            defaultStates.put(blockTypeKey, defaultState);

            Map<Map<String, String>, RegistryBlockState> statesByProperties = new HashMap<>();
            for (int stateIndex : data.states()) {
                RegistryBlockState state = this.byIndex(stateIndex);
                statesByProperties.put(state.properties(), state);
            }

            possibleStates.put(blockTypeKey, Map.copyOf(statesByProperties));
        }

        this.possibleStates = Map.copyOf(possibleStates);
        this.defaultStates = Map.copyOf(defaultStates);
    }

    @Override
    public BlockState defaultBlockState(Reference<BlockType> blockType) {
        Objects.requireNonNull(blockType, "block type");

        Key blockTypeKey = blockType.key();
        RegistryBlockState state = this.defaultStates.get(blockTypeKey);

        if (state == null) {
            throw new IllegalArgumentException(String.format(
                    "Could not find a default block state for \"%s\" block type",
                    blockTypeKey
            ));
        }

        return state;
    }

    @Override
    public BlockState blockState(Reference<BlockType> blockType, Map<String, String> properties) {
        Objects.requireNonNull(blockType, "block type");
        Objects.requireNonNull(properties, "properties");

        Key blockTypeKey = blockType.key();
        Map<Map<String, String>, RegistryBlockState> statesByProperties = this.possibleStates.get(blockTypeKey);

        if (statesByProperties == null) {
            throw new IllegalArgumentException(String.format(
                    "Could not find possible block states for \"%s\" block type",
                    blockTypeKey
            ));
        }

        RegistryBlockState state = statesByProperties.get(properties);
        if (state == null) {
            throw new IllegalArgumentException(String.format(
                    "Could not find a block state with properties of %s for \"%s\" block type",
                    properties,
                    blockTypeKey
            ));
        }

        return state;
    }

    public int indexOf(BlockState blockState) {
        if (!(blockState instanceof RegistryBlockState state)) {
            throw new IllegalArgumentException("The specified block state is not a valid block state");
        }

        Integer index = this.blockStateToIndex.get(state);
        if (index == null) {
            throw new IllegalArgumentException("Could not find an index for the specified block state");
        }

        return index;
    }

    public RegistryBlockState byIndex(int index) {
        try {
            return this.blockStates.get(index);
        } catch (IndexOutOfBoundsException exception) {
            throw new IllegalArgumentException(String.format(
                    "Block state with %d index has not been registered",
                    index
            ), exception);
        }
    }

    public record RegistryBlockState(
            Holder<BlockType> blockType,
            Map<String, String> properties,
            boolean air,
            boolean hasFluidState,
            boolean blocksMotion,
            boolean leaves
    ) implements BlockState {
        public RegistryBlockState {
            Objects.requireNonNull(blockType, "block type");
            properties = Map.copyOf(Objects.requireNonNull(properties, "properties"));
        }
    }

    private record BlockStateData(
            Map<String, String> properties,
            boolean air,
            boolean hasFluidState,
            boolean blocksMotion,
            boolean leaves
    ) {
        private BlockStateData {
            properties = Map.copyOf(Objects.requireNonNull(properties, "properties"));
        }
    }

    private record BlockData(List<Key> requiredFeatureFlags, int defaultState, List<Integer> states) {
        private BlockData {
            requiredFeatureFlags = List.copyOf(Objects.requireNonNull(requiredFeatureFlags, "required feature flags"));
            states = List.copyOf(Objects.requireNonNull(states, "states"));
        }
    }
}
