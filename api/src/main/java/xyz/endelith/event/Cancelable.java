package xyz.endelith.event;

public abstract class Cancelable extends Event {

    private boolean canceled;

    public boolean isCanceled() {
        return this.canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }
}
