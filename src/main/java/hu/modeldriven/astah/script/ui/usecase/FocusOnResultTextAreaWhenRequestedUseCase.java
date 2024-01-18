package hu.modeldriven.astah.script.ui.usecase;

import hu.modeldriven.astah.script.common.eventbus.EventBus;
import hu.modeldriven.astah.script.common.usecase.UseCase;
import hu.modeldriven.astah.script.ui.event.ResultTextareaFocusRequestedEvent;

import javax.swing.*;

public class FocusOnResultTextAreaWhenRequestedUseCase implements UseCase {

    private final JTabbedPane tabbedPane;

    public FocusOnResultTextAreaWhenRequestedUseCase(EventBus eventBus, JTabbedPane tabbedPane) {
        this.tabbedPane = tabbedPane;
        eventBus.subscribe(ResultTextareaFocusRequestedEvent.class, this::onResultTextareaFocusRequested);
    }

    private void onResultTextareaFocusRequested(ResultTextareaFocusRequestedEvent event) {
        SwingUtilities.invokeLater(() -> this.tabbedPane.setSelectedIndex(1));
    }

}
