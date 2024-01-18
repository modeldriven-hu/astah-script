package hu.modeldriven.astah.script.ui.event;

import hu.modeldriven.core.eventbus.Event;

import java.util.List;

public class ListResultCreatedEvent implements Event {

    private final List<Object> list;

    public ListResultCreatedEvent(List<Object> list) {
        this.list = list;
    }

    public List<Object> getList() {
        return list;
    }
}
