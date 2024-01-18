package hu.modeldriven.astah.script.ui.event;

import hu.modeldriven.astah.script.common.script.ScriptExecutionException;
import hu.modeldriven.core.eventbus.Event;

public class ScriptExecutionFailedEvent implements Event {

    private final ScriptExecutionException exception;

    public ScriptExecutionFailedEvent(ScriptExecutionException exception) {
        this.exception = exception;
    }

    public boolean hasValidLine() {
        return exception.getLine() != ScriptExecutionException.MISSING_LINE;
    }

    public int getLine() {
        return exception.getLine();
    }

    public String getMessage() {
        return exception.getErrorMessage();
    }

}
