package xyz.endelith.world.block;

import java.util.Map;
import xyz.endelith.registry.holder.Holder;

public interface BlockState {

    Holder<BlockType> blockType();

    Map<String, String> properties();
}
