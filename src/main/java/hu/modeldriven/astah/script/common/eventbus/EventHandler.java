package hu.modeldriven.astah.script.common.eventbus;

@FunctionalInterface
public interface EventHandler<T extends Event> {
    void handleEvent(T e);
}
