package hu.modeldriven.astah.script.ui.usecase;

import hu.modeldriven.astah.script.common.eventbus.EventBus;
import hu.modeldriven.astah.script.common.usecase.UseCase;
import hu.modeldriven.astah.script.ui.event.ExceptionOccurredEvent;
import hu.modeldriven.astah.script.ui.event.ScriptExecutionFailedEvent;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import javax.swing.*;
import javax.swing.text.BadLocationException;

public class SelectErroneousLineInEditorUseCase implements UseCase {

    private final EventBus eventBus;
    private final RSyntaxTextArea textArea;

    public SelectErroneousLineInEditorUseCase(EventBus eventBus, RSyntaxTextArea textArea) {
        this.eventBus = eventBus;
        this.textArea = textArea;
        eventBus.subscribe(ScriptExecutionFailedEvent.class, this::onScriptExecutionFailed);
    }

    public void onScriptExecutionFailed(ScriptExecutionFailedEvent event) {
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

}
