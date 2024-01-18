package hu.modeldriven.astah.script.ui.usecase;

import hu.modeldriven.astah.script.common.eventbus.EventBus;
import hu.modeldriven.astah.script.common.usecase.UseCase;
import hu.modeldriven.astah.script.ui.event.EmptyResultCreatedEvent;
import hu.modeldriven.astah.script.ui.event.ResultTextareaFocusRequestedEvent;

import javax.swing.*;

public class DisplayEmptyResultInOutputTextAreaUseCase implements UseCase {

    private final EventBus eventBus;
    private final JTextArea textArea;

    public DisplayEmptyResultInOutputTextAreaUseCase(EventBus eventBus, JTextArea textArea) {
        this.eventBus = eventBus;
        this.textArea = textArea;
        eventBus.subscribe(EmptyResultCreatedEvent.class, this::onEmptyResultCreated);
    }

    public void onEmptyResultCreated(EmptyResultCreatedEvent event) {
        eventBus.publish(new ResultTextareaFocusRequestedEvent());
        SwingUtilities.invokeLater(() -> textArea.setText("Empty result provided..."));
    }

}
