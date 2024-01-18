package hu.modeldriven.core.eventbus;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings({"squid:S3740"})
public class EventBus {

    private final Map<Class<? extends Event>, Set<EventHandler<? extends Event>>> subscriptions = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public void publish(Event event) {
        for (Map.Entry<Class<? extends Event>, Set<EventHandler<? extends Event>>> entry : subscriptions.entrySet()) {
            if (entry.getKey().equals(event.getClass())) {
                for (EventHandler handler : entry.getValue()) {
                    handler.handleEvent(event);
                }
                return;
            }
        }
    }

    public <T extends Event> void subscribe(Class<T> eventClass, EventHandler<T> eventHandler) {
        subscriptions.computeIfAbsent(eventClass, k -> new HashSet<>());
        subscriptions.get(eventClass).add(eventHandler);
    }

    public <T extends Event> void subscribe(EventHandler<T> eventHandler) {
        List<Class<? extends Event>> subscribedEvents = eventHandler.subscribedEvents();

        for (Class<? extends Event> eventClass : subscribedEvents) {
            subscriptions.computeIfAbsent(eventClass, k -> new HashSet<>());
            subscriptions.get(eventClass).add(eventHandler);
        }
    }

}
