package xyz.endelith.server.world.block.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.kyori.adventure.key.Key;
import xyz.endelith.registry.holder.Holder;
import xyz.endelith.registry.holder.HolderSet;
import xyz.endelith.world.block.BlockType;
import xyz.endelith.world.block.entity.BlockEntityType;

public record BlockEntityTypeImpl(HolderSet<BlockType> validBlocks) implements BlockEntityType {

    public BlockEntityTypeImpl {
        Objects.requireNonNull(validBlocks, "validBlocks");
    }

    public static BlockEntityTypeImpl convert(List<Key> validBlocks) {
        List<Holder<BlockType>> holders = new ArrayList<>();
        for (Key validBlock : validBlocks) {
            holders.add(new Holder.Reference<>(validBlock));
        }
        return new BlockEntityTypeImpl(new HolderSet.Direct<>(holders));
    }
}
