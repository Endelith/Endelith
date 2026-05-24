package xyz.endelith.server.registry.codec.world.block.banner;

import xyz.endelith.cosine.codec.Codec;
import xyz.endelith.cosine.codec.StructCodec;
import xyz.endelith.world.block.banner.BannerPattern;

public final class BannerPatternCodec {

    public static final StructCodec<BannerPattern> CODEC = StructCodec.of(
            "asset_id", Codec.KEY, BannerPattern::asset,
            "translation_key", Codec.STRING, BannerPattern::translationKey,
            BannerPattern::new
    );

    private BannerPatternCodec() {
    }
}
