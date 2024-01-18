package hu.modeldriven.astah.script.ui.usecase;

import hu.modeldriven.astah.script.ui.event.ResultTextareaFocusRequestedEvent;
import hu.modeldriven.core.eventbus.Event;
import hu.modeldriven.core.eventbus.EventHandler;

import javax.swing.*;
import java.util.Collections;
import java.util.List;

public class FocusOnResultTextAreaWhenRequestedUseCase implements EventHandler<ResultTextareaFocusRequestedEvent> {

    private final JTabbedPane tabbedPane;

    public FocusOnResultTextAreaWhenRequestedUseCase(JTabbedPane tabbedPane) {
        this.tabbedPane = tabbedPane;
    }

    @Override
    public void handleEvent(ResultTextareaFocusRequestedEvent event) {
        SwingUtilities.invokeLater(() -> this.tabbedPane.setSelectedIndex(1));
    }

    @Override
    public List<Class<? extends Event>> subscribedEvents() {
        return Collections.singletonList(ResultTextareaFocusRequestedEvent.class);
    }
}
