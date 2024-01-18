package hu.modeldriven.astah.script.ui.usecase;

import hu.modeldriven.astah.script.common.eventbus.EventBus;
import hu.modeldriven.astah.script.common.usecase.UseCase;
import hu.modeldriven.astah.script.ui.event.ClearFilterRequestedEvent;

import javax.swing.*;
import javax.swing.table.TableRowSorter;

public class ClearFilterTableUseCase implements UseCase {

    private final JTable table;

    public ClearFilterTableUseCase(EventBus eventBus, JTable table) {
        this.table = table;
        eventBus.subscribe(ClearFilterRequestedEvent.class, this::clearFilterRequested);
    }

    private void clearFilterRequested(ClearFilterRequestedEvent event) {

        SwingUtilities.invokeLater(() -> {
            TableRowSorter sorter = new TableRowSorter<>(table.getModel());
            sorter.setRowFilter(null);
            table.setRowSorter(sorter);
        });
    }

}
