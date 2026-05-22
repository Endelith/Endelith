package xyz.endelith.server.registry.codec.entity.variant.cat;

import xyz.endelith.cosine.codec.Codec;
import xyz.endelith.cosine.codec.StructCodec;
import xyz.endelith.entity.variant.cat.CatVariant;

public final class CatVariantCodec {

    private CatVariantCodec() {
    }

    public static final StructCodec<CatVariant> CODEC = StructCodec.of(
            "asset_id", Codec.KEY, CatVariant::asset,
            "baby_asset_id", Codec.KEY, CatVariant::babyAsset,
            CatVariant::new
    );
}
