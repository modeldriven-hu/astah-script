package hu.modeldriven.astah.script.ui.usecase;

import hu.modeldriven.astah.script.common.eventbus.EventBus;
import hu.modeldriven.astah.script.common.usecase.UseCase;
import hu.modeldriven.astah.script.ui.event.ResultTextareaFocusRequestedEvent;
import hu.modeldriven.astah.script.ui.event.StringResultCreatedEvent;

import javax.swing.*;

public class DisplayStringResultInOutputTextAreaUseCase implements UseCase {

    private final EventBus eventBus;
    private final JTextArea textArea;

    public DisplayStringResultInOutputTextAreaUseCase(EventBus eventBus, JTextArea textArea) {
        this.eventBus = eventBus;
        this.textArea = textArea;
        eventBus.subscribe(StringResultCreatedEvent.class, this::onStringResultCreated);
    }

    public void onStringResultCreated(StringResultCreatedEvent event) {
        eventBus.publish(new ResultTextareaFocusRequestedEvent());
        SwingUtilities.invokeLater(() -> textArea.setText(event.getResult()));
    }

}
