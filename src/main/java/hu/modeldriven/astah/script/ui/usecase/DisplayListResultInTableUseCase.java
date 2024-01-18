package hu.modeldriven.astah.script.ui.usecase;

import hu.modeldriven.astah.script.common.ui.SimpleListTableModel;
import hu.modeldriven.astah.script.ui.event.ListResultCreatedEvent;
import hu.modeldriven.astah.script.ui.event.ResultGridFocusRequestedEvent;
import hu.modeldriven.core.eventbus.Event;
import hu.modeldriven.core.eventbus.EventBus;
import hu.modeldriven.core.eventbus.EventHandler;

import javax.swing.*;
import java.util.Collections;
import java.util.List;

public class DisplayListResultInTableUseCase implements EventHandler<ListResultCreatedEvent> {

    private final EventBus eventBus;
    private final JTable table;

    public DisplayListResultInTableUseCase(EventBus eventBus, JTable table) {
        this.eventBus = eventBus;
        this.table = table;
    }

    @Override
    public void handleEvent(ListResultCreatedEvent event) {
        eventBus.publish(new ResultGridFocusRequestedEvent());

        SwingUtilities.invokeLater(() -> {
            this.table.setRowSorter(null);

            SimpleListTableModel model = new SimpleListTableModel(event.getList());
            this.table.setModel(model);

            model.fireTableDataChanged();
        });
    }

    @Override
    public List<Class<? extends Event>> subscribedEvents() {
        return Collections.singletonList(ListResultCreatedEvent.class);
    }
}
