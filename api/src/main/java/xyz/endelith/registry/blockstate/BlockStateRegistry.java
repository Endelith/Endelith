package xyz.endelith.registry.blockstate;

import java.util.Map;
import xyz.endelith.registry.holder.Holder;
import xyz.endelith.world.block.BlockState;
import xyz.endelith.world.block.BlockType;

public interface BlockStateRegistry {

    BlockState defaultBlockState(Holder.Reference<BlockType> blockType);

    BlockState blockState(Holder.Reference<BlockType> blockType, Map<String, String> properties);
}
