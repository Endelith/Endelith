package xyz.endelith.world.block.entity;

import xyz.endelith.registry.holder.HolderSet;
import xyz.endelith.world.block.BlockType;

public interface BlockEntityType {

    HolderSet<BlockType> validBlocks();
}
