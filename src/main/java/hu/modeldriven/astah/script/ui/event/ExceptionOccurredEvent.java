package hu.modeldriven.astah.script.ui.event;

import hu.modeldriven.core.eventbus.Event;

public class ExceptionOccurredEvent implements Event {

    private final Exception exception;

    public ExceptionOccurredEvent(Exception exception) {
        this.exception = exception;
    }

    public String getMessage() {
        return exception.getMessage();
    }
}

