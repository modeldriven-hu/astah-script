package hu.modeldriven.astah.script.ui.usecase;

import hu.modeldriven.astah.script.ui.event.EmptyResultCreatedEvent;
import hu.modeldriven.astah.script.ui.event.ResultTextareaFocusRequestedEvent;
import hu.modeldriven.core.eventbus.Event;
import hu.modeldriven.core.eventbus.EventBus;
import hu.modeldriven.core.eventbus.EventHandler;

import javax.swing.*;
import java.util.Collections;
import java.util.List;

public class DisplayEmptyResultInOutputTextAreaUseCase implements EventHandler<EmptyResultCreatedEvent> {

    private final EventBus eventBus;
    private final JTextArea textArea;

    public DisplayEmptyResultInOutputTextAreaUseCase(EventBus eventBus, JTextArea textArea) {
        this.eventBus = eventBus;
        this.textArea = textArea;
    }

    @Override
    public void handleEvent(EmptyResultCreatedEvent event) {
        eventBus.publish(new ResultTextareaFocusRequestedEvent());
        SwingUtilities.invokeLater(() -> textArea.setText("Empty result provided..."));
    }

    @Override
    public List<Class<? extends Event>> subscribedEvents() {
        return Collections.singletonList(EmptyResultCreatedEvent.class);
    }
}
