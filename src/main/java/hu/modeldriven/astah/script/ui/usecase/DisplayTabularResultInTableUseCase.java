package hu.modeldriven.astah.script.ui.usecase;

import hu.modeldriven.astah.script.common.ui.TabularResultTableModel;
import hu.modeldriven.astah.script.ui.event.ResultGridFocusRequestedEvent;
import hu.modeldriven.astah.script.ui.event.TabularResultCreatedEvent;
import hu.modeldriven.core.eventbus.Event;
import hu.modeldriven.core.eventbus.EventBus;
import hu.modeldriven.core.eventbus.EventHandler;

import javax.swing.*;
import java.util.Collections;
import java.util.List;

public class DisplayTabularResultInTableUseCase implements EventHandler<TabularResultCreatedEvent> {

    private final EventBus eventBus;
    private final JTable table;

    public DisplayTabularResultInTableUseCase(EventBus eventBus, JTable table) {
        this.eventBus = eventBus;
        this.table = table;
    }

    @Override
    public void handleEvent(TabularResultCreatedEvent event) {
        eventBus.publish(new ResultGridFocusRequestedEvent());

        SwingUtilities.invokeLater(() -> {
            this.table.setRowSorter(null);
            TabularResultTableModel model = new TabularResultTableModel(event.getTabularResult());
            this.table.setModel(model);
            model.fireTableDataChanged();
        });
    }

    @Override
    public List<Class<? extends Event>> subscribedEvents() {
        return Collections.singletonList(TabularResultCreatedEvent.class);
    }
}
