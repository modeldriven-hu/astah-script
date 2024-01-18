package hu.modeldriven.astah.script.ui.usecase;

import hu.modeldriven.astah.script.common.eventbus.EventBus;
import hu.modeldriven.astah.script.common.usecase.UseCase;
import hu.modeldriven.astah.script.ui.event.ResultTextareaFocusRequestedEvent;
import hu.modeldriven.astah.script.ui.event.ScriptExecutionFailedEvent;

import javax.swing.*;

public class DisplayScriptExecutionFailedInOutputTextAreaUseCase implements UseCase {

    private final EventBus eventBus;
    private final JTextArea textArea;

    public DisplayScriptExecutionFailedInOutputTextAreaUseCase(EventBus eventBus, JTextArea textArea) {
        this.eventBus = eventBus;
        this.textArea = textArea;
        eventBus.subscribe(ScriptExecutionFailedEvent.class, this::onScriptExecutionFailed);
    }

    public void onScriptExecutionFailed(ScriptExecutionFailedEvent event) {
        eventBus.publish(new ResultTextareaFocusRequestedEvent());
        SwingUtilities.invokeLater(() -> textArea.setText(event.getMessage()));
    }

}
