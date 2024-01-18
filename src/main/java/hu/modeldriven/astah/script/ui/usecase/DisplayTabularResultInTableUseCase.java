package hu.modeldriven.astah.script.ui.usecase;

import hu.modeldriven.astah.script.common.eventbus.EventBus;
import hu.modeldriven.astah.script.common.ui.TabularResultTableModel;
import hu.modeldriven.astah.script.common.usecase.UseCase;
import hu.modeldriven.astah.script.ui.event.ResultGridFocusRequestedEvent;
import hu.modeldriven.astah.script.ui.event.TabularResultCreatedEvent;

import javax.swing.*;

public class DisplayTabularResultInTableUseCase implements UseCase {

    private final EventBus eventBus;
    private final JTable table;

    public DisplayTabularResultInTableUseCase(EventBus eventBus, JTable table) {
        this.eventBus = eventBus;
        this.table = table;
        eventBus.subscribe(TabularResultCreatedEvent.class, this::onTabularResultCreated);
    }

    private void onTabularResultCreated(TabularResultCreatedEvent event) {

        eventBus.publish(new ResultGridFocusRequestedEvent());

        SwingUtilities.invokeLater(() -> {
            this.table.setRowSorter(null);
            TabularResultTableModel model = new TabularResultTableModel(event.getTabularResult());
            this.table.setModel(model);
            model.fireTableDataChanged();
        });
    }


}
