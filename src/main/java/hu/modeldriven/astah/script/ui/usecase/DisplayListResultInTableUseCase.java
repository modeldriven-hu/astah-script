package hu.modeldriven.astah.script.ui.usecase;

import hu.modeldriven.astah.script.common.eventbus.EventBus;
import hu.modeldriven.astah.script.common.ui.SimpleListTableModel;
import hu.modeldriven.astah.script.common.usecase.UseCase;
import hu.modeldriven.astah.script.ui.event.ListResultCreatedEvent;
import hu.modeldriven.astah.script.ui.event.ResultGridFocusRequestedEvent;

import javax.swing.*;

public class DisplayListResultInTableUseCase implements UseCase {

    private final EventBus eventBus;
    private final JTable table;

    public DisplayListResultInTableUseCase(EventBus eventBus, JTable table) {
        this.eventBus = eventBus;
        this.table = table;
        eventBus.subscribe(ListResultCreatedEvent.class, this::onListResultCreated);
    }

    void onListResultCreated(ListResultCreatedEvent event) {

        eventBus.publish(new ResultGridFocusRequestedEvent());

        SwingUtilities.invokeLater(() -> {
            this.table.setRowSorter(null);

            SimpleListTableModel model = new SimpleListTableModel(event.getList());
            this.table.setModel(model);

            model.fireTableDataChanged();
        });
    }

}
