package xyz.endelith.server.registry.codec.entity.damage;

import xyz.endelith.cosine.codec.Codec;
import xyz.endelith.cosine.codec.StructCodec;
import xyz.endelith.entity.damage.DamageType;
import xyz.endelith.entity.damage.DamageType.DamageEffects;
import xyz.endelith.entity.damage.DamageType.DamageScalingType;
import xyz.endelith.entity.damage.DamageType.DeathMessageType;

public final class DamageTypeCodec {

    public static final StructCodec<DamageType> CODEC = StructCodec.of(
            "message_id", Codec.STRING, DamageType::messageId,
            "scaling", Codec.enumOf(DamageScalingType.class), DamageType::scalingType,
            "exhaustion", Codec.FLOAT, DamageType::exhaustion,
            "effects", Codec.enumOf(DamageEffects.class).defaultValue(DamageEffects.HURT), DamageType::effects,
            "death_message_type", Codec.enumOf(DeathMessageType.class).defaultValue(DeathMessageType.DEFAULT),
            DamageType::deathMessageType,
            DamageType::new
    );

    private DamageTypeCodec() {
    }
}
