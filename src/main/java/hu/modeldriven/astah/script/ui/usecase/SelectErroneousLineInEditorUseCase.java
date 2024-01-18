package hu.modeldriven.astah.script.ui.usecase;

import hu.modeldriven.astah.script.ui.event.ExceptionOccurredEvent;
import hu.modeldriven.astah.script.ui.event.ScriptExecutionFailedEvent;
import hu.modeldriven.core.eventbus.Event;
import hu.modeldriven.core.eventbus.EventBus;
import hu.modeldriven.core.eventbus.EventHandler;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.util.Collections;
import java.util.List;

public class SelectErroneousLineInEditorUseCase implements EventHandler<ScriptExecutionFailedEvent> {

    private final EventBus eventBus;
    private final RSyntaxTextArea textArea;

    public SelectErroneousLineInEditorUseCase(EventBus eventBus, RSyntaxTextArea textArea) {
        this.eventBus = eventBus;
        this.textArea = textArea;
    }

    @Override
    public void handleEvent(ScriptExecutionFailedEvent event) {
        SwingUtilities.invokeLater(() -> {

            try {

                if (event.hasValidLine()) {
                    int line = event.getLine();
                    textArea.setCaretPosition(textArea.getLineStartOffset(line - 1));
                    textArea.requestFocus();
                }

            } catch (BadLocationException e) {
                eventBus.publish(new ExceptionOccurredEvent(e));
            }

        });
    }

    @Override
    public List<Class<? extends Event>> subscribedEvents() {
        return Collections.singletonList(ScriptExecutionFailedEvent.class);
    }
}
