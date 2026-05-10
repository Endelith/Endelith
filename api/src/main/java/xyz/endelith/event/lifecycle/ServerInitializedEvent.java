package xyz.endelith.event.lifecycle;

import xyz.endelith.MinecraftServer;
import xyz.endelith.event.Event;

public interface ServerInitializedEvent extends Event {

    MinecraftServer server();
}
