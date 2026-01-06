package xyz.endelith.server.configuration.unparsed;

import eu.okaeri.configs.serdes.OkaeriSerdes;
import eu.okaeri.configs.serdes.SerdesRegistry;
import eu.okaeri.configs.serdes.commons.SerdesCommons;
import xyz.endelith.server.configuration.unparsed.serializer.KeySerializer;

public final class UnparsedConfigurationSerdes implements OkaeriSerdes {
    @Override
    public void register(SerdesRegistry registry) {
        registry.register(new SerdesCommons());
        registry.register(new KeySerializer());
    }
}
