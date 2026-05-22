# Registry events example

`RegistryReference<V>` is the single identity for a registry. Use the same reference for
registry access and for registry initialize events.

## Define a registry reference

```java
import xyz.endelith.registry.reference.RegistryReference;

public final class ExampleRegistries {

    public static final RegistryReference<ExampleValue> EXAMPLE_VALUES =
            RegistryReference.create("example_values");

    private ExampleRegistries() {
    }
}
```

## Listen during bootstrap

```java
import net.kyori.adventure.key.Key;
import xyz.endelith.plugin.Plugin;
import xyz.endelith.plugin.bootstrap.BootstrapContext;
import xyz.endelith.registry.event.RegistryEvents;

public final class ExamplePlugin extends Plugin {

    @Override
    public void bootstrap(BootstrapContext context) {
        context.eventManager().listen(
                RegistryEvents.initialize(ExampleRegistries.EXAMPLE_VALUES),
                event -> event.register(
                        Key.key("example", "first_value"),
                        new ExampleValue(),
                        null
                )
        );
    }
}
```

## Fire from registry initialization code

```java
bootstrapEventManager.fire(
        RegistryEvents.initialize(reference),
        new RegistryInitializeEvent<>(reference, access)
);
```

The important part is that the event key is derived from the same
`RegistryReference<V>`:

```java
RegistryEvents.initialize(ExampleRegistries.EXAMPLE_VALUES);
```

So callers do not need to duplicate a registry id in `RegistryReference` and again in
`RegistryEvents`.
