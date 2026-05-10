package xyz.endelith.event;

public interface Cancellable {

    boolean isCancelled();

    void setCancelled(boolean cancelled);
}
