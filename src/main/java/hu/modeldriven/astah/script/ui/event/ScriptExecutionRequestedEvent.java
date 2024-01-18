package hu.modeldriven.astah.script.ui.event;

import hu.modeldriven.astah.script.common.eventbus.Event;

public class ScriptExecutionRequestedEvent implements Event {

    private final String language;
    private final String script;

    public ScriptExecutionRequestedEvent(String language, String script) {
        this.language = language;
        this.script = script;
    }

    public String getLanguage() {
        return language;
    }

    public String getScript() {
        return script;
    }
}
