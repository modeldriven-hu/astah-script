package hu.modeldriven.astah.script.ui.usecase;

import hu.modeldriven.astah.script.common.eventbus.EventBus;
import hu.modeldriven.astah.script.common.usecase.UseCase;
import hu.modeldriven.astah.script.ui.event.ResultGridFocusRequestedEvent;

import javax.swing.*;

public class FocusOnResultGridWhenRequestedUseCase implements UseCase {

    private final JTabbedPane tabbedPane;

    public FocusOnResultGridWhenRequestedUseCase(EventBus eventBus, JTabbedPane tabbedPane) {
        this.tabbedPane = tabbedPane;
        eventBus.subscribe(ResultGridFocusRequestedEvent.class, this::onResultGridFocusRequested);
    }

    private void onResultGridFocusRequested(ResultGridFocusRequestedEvent event) {
        SwingUtilities.invokeLater(() -> this.tabbedPane.setSelectedIndex(0));
    }

}
