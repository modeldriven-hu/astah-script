package hu.modeldriven.astah.script.ui.usecase;

import hu.modeldriven.astah.script.ui.event.ExceptionOccurredEvent;
import hu.modeldriven.astah.script.ui.event.ResultTextareaFocusRequestedEvent;
import hu.modeldriven.core.eventbus.Event;
import hu.modeldriven.core.eventbus.EventHandler;
import hu.modeldriven.core.eventbus.EventBus;

import javax.swing.*;
import java.util.Collections;
import java.util.List;

public class DisplayExceptionInOutputTextAreaUseCase implements EventHandler<ExceptionOccurredEvent> {

    private final EventBus eventBus;
    private final JTextArea textArea;

    public DisplayExceptionInOutputTextAreaUseCase(EventBus eventBus, JTextArea textArea) {
        this.eventBus = eventBus;
        this.textArea = textArea;
    }

    @Override
    public void handleEvent(ExceptionOccurredEvent event) {
        eventBus.publish(new ResultTextareaFocusRequestedEvent());
        SwingUtilities.invokeLater(() -> textArea.setText(event.getMessage()));
    }

    @Override
    public List<Class<? extends Event>> subscribedEvents() {
        return Collections.singletonList(ExceptionOccurredEvent.class);
    }
}
