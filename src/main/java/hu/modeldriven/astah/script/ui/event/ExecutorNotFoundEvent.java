package hu.modeldriven.astah.script.ui.event;

import hu.modeldriven.astah.script.common.eventbus.Event;
import hu.modeldriven.astah.script.common.script.ExecutorNotFoundException;

public class ExecutorNotFoundEvent implements Event {

    private final ExecutorNotFoundException exception;

    public ExecutorNotFoundEvent(ExecutorNotFoundException exception) {
        this.exception = exception;
    }

}
