package xyz.endelith.server.registry.codec.entity.variant.cow;

import xyz.endelith.cosine.codec.Codec;
import xyz.endelith.cosine.codec.StructCodec;
import xyz.endelith.entity.variant.cow.CowVariant;
import xyz.endelith.entity.variant.cow.CowVariant.ModelType;

public final class CowVariantCodec {

    public static final StructCodec<CowVariant> CODEC = StructCodec.of(
            "asset_id", Codec.KEY, CowVariant::asset,
            "baby_asset_id", Codec.KEY, CowVariant::babyAsset,
            "model", Codec.enumOf(ModelType.class).defaultValue(ModelType.NORMAL), CowVariant::modelType,
            CowVariant::new
    );

    private CowVariantCodec() {
    }
}
