package hu.modeldriven.astah.script.ui.event;

import java.util.List;

import hu.modeldriven.core.eventbus.Event;

public class ListResultCreatedEvent implements Event {

    private final List<Object> list;

    public ListResultCreatedEvent(List<Object> list) {
        this.list = list;
    }

    public List<Object> getList() {
        return list;
    }
}
