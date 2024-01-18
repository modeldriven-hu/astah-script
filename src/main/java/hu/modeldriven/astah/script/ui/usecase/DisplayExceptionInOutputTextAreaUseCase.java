package hu.modeldriven.astah.script.ui.usecase;

import hu.modeldriven.astah.script.common.eventbus.EventBus;
import hu.modeldriven.astah.script.common.usecase.UseCase;
import hu.modeldriven.astah.script.ui.event.ExceptionOccurredEvent;
import hu.modeldriven.astah.script.ui.event.ResultTextareaFocusRequestedEvent;

import javax.swing.*;

public class DisplayExceptionInOutputTextAreaUseCase implements UseCase {

    private final EventBus eventBus;
    private final JTextArea textArea;

    public DisplayExceptionInOutputTextAreaUseCase(EventBus eventBus, JTextArea textArea) {
        this.eventBus = eventBus;
        this.textArea = textArea;
        eventBus.subscribe(ExceptionOccurredEvent.class, this::onExceptionOccurred);
    }

    public void onExceptionOccurred(ExceptionOccurredEvent event) {
        eventBus.publish(new ResultTextareaFocusRequestedEvent());
        SwingUtilities.invokeLater(() -> textArea.setText(event.getMessage()));
    }

}
