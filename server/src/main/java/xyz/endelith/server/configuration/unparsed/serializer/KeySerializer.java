package xyz.endelith.server.configuration.unparsed.serializer;

import java.util.Objects;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import net.kyori.adventure.key.Key;

public final class KeySerializer implements ObjectSerializer<Key> {

    @Override
    public boolean supports(Class<?> type) {
        return Key.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(Key object, SerializationData data, GenericsDeclaration generics) {
        data.setValue(object.asString());
    }

    @Override
    public Key deserialize(DeserializationData data, GenericsDeclaration generics) {
        return Key.key(Objects.requireNonNull(data.getValue(String.class), "value"));
    }    
}
