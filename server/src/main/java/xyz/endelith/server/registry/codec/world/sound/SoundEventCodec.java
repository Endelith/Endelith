package xyz.endelith.server.registry.codec.world.sound;

import xyz.endelith.cosine.codec.Codec;
import xyz.endelith.cosine.codec.StructCodec;
import xyz.endelith.registry.holder.Holder;
import xyz.endelith.world.sound.SoundEvent;

public final class SoundEventCodec {

    public static final StructCodec<SoundEvent> CODEC = StructCodec.of(
            "sound", Codec.KEY, SoundEvent::sound,
            SoundEvent::new
    );

    public static final Codec<Holder<SoundEvent>> HOLDER_CODEC =
            Codec.KEY.transform(Holder.Reference::new, ref -> ((Holder.Reference<SoundEvent>) ref).key());

    private SoundEventCodec() {
    }
}
