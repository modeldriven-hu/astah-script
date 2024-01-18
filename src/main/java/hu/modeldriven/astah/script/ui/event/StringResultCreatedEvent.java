package hu.modeldriven.astah.script.ui.event;

import hu.modeldriven.astah.script.common.eventbus.Event;

public class StringResultCreatedEvent implements Event {

    String result;

    public StringResultCreatedEvent(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }
}
