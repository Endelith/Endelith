package xyz.endelith.registry.holder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import net.kyori.adventure.key.Key;
import xyz.endelith.registry.MinecraftRegistry;

public interface HolderSet<V> {    
    
    List<Holder<V>> contents(MinecraftRegistry<V> registry);
    
    record Direct<V>(List<Holder<V>> contents) implements HolderSet<V> {

        public Direct {
            contents = List.copyOf(Objects.requireNonNull(contents, "contents"));
        }

        @Override
        public List<Holder<V>> contents(MinecraftRegistry<V> registry) {
            return contents;
        }
    }

    record Named<V>(Key tagKey) implements HolderSet<V> {

        public Named {
            Objects.requireNonNull(tagKey, "tag key");
        }

        @Override
        public List<Holder<V>> contents(MinecraftRegistry<V> registry) {
            List<Holder<V>> holders = new ArrayList<>();

            for (Key key : registry.keySet()) {
                if (!registry.tagsFor(key).contains(tagKey)) continue;
                holders.add(new Holder.Reference<>(key));
            }

            return List.copyOf(holders);
        } 
    }
}
