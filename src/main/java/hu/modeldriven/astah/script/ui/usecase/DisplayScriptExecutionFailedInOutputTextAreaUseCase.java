package hu.modeldriven.astah.script.ui.usecase;

import hu.modeldriven.astah.script.ui.event.ResultTextareaFocusRequestedEvent;
import hu.modeldriven.astah.script.ui.event.ScriptExecutionFailedEvent;
import hu.modeldriven.core.eventbus.Event;
import hu.modeldriven.core.eventbus.EventBus;
import hu.modeldriven.core.eventbus.EventHandler;

import javax.swing.*;
import java.util.Collections;
import java.util.List;

public class DisplayScriptExecutionFailedInOutputTextAreaUseCase implements EventHandler<ScriptExecutionFailedEvent> {

    private final EventBus eventBus;
    private final JTextArea textArea;

    public DisplayScriptExecutionFailedInOutputTextAreaUseCase(EventBus eventBus, JTextArea textArea) {
        this.eventBus = eventBus;
        this.textArea = textArea;
    }


    @Override
    public void handleEvent(ScriptExecutionFailedEvent event) {
        eventBus.publish(new ResultTextareaFocusRequestedEvent());
        SwingUtilities.invokeLater(() -> textArea.setText(event.getMessage()));
    }

    @Override
    public List<Class<? extends Event>> subscribedEvents() {
        return Collections.singletonList(ScriptExecutionFailedEvent.class);
    }
}
