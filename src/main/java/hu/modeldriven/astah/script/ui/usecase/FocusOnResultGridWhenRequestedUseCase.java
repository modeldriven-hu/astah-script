package hu.modeldriven.astah.script.ui.usecase;

import hu.modeldriven.astah.script.ui.event.ResultGridFocusRequestedEvent;
import hu.modeldriven.core.eventbus.Event;
import hu.modeldriven.core.eventbus.EventHandler;

import javax.swing.*;
import java.util.Collections;
import java.util.List;

public class FocusOnResultGridWhenRequestedUseCase implements EventHandler<ResultGridFocusRequestedEvent> {

    private final JTabbedPane tabbedPane;

    public FocusOnResultGridWhenRequestedUseCase(JTabbedPane tabbedPane) {
        this.tabbedPane = tabbedPane;
    }

    @Override
    public void handleEvent(ResultGridFocusRequestedEvent event) {
        SwingUtilities.invokeLater(() -> this.tabbedPane.setSelectedIndex(0));
    }

    @Override
    public List<Class<? extends Event>> subscribedEvents() {
        return Collections.singletonList(ResultGridFocusRequestedEvent.class);
    }
}
