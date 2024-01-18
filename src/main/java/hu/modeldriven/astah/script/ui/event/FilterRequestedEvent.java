package hu.modeldriven.astah.script.ui.event;

import hu.modeldriven.core.eventbus.Event;

public class FilterRequestedEvent implements Event {

    private final String filter;

    public FilterRequestedEvent(String filter) {
        this.filter = filter;
    }

    public String getFilter() {
        return filter;
    }
}
