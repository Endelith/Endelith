package xyz.endelith.server.registry.codec.entity.variant.chicken;

import xyz.endelith.cosine.codec.Codec;
import xyz.endelith.cosine.codec.StructCodec;
import xyz.endelith.entity.variant.chicken.ChickenVariant;
import xyz.endelith.entity.variant.chicken.ChickenVariant.ModelType;

public final class ChickenVariantCodec {

    public static final StructCodec<ChickenVariant> CODEC = StructCodec.of(
            "asset_id", Codec.KEY, ChickenVariant::asset,
            "baby_asset_id", Codec.KEY, ChickenVariant::babyAsset,
            "model", Codec.enumOf(ModelType.class).defaultValue(ModelType.NORMAL), ChickenVariant::modelType,
            ChickenVariant::new
    );

    private ChickenVariantCodec() {
    }
}
