package hu.modeldriven.astah.script.ui.usecase;

import hu.modeldriven.astah.script.ui.event.ClearFilterRequestedEvent;
import hu.modeldriven.core.eventbus.Event;
import hu.modeldriven.core.eventbus.EventHandler;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.util.Collections;
import java.util.List;

public class ClearFilterTableUseCase implements EventHandler<ClearFilterRequestedEvent> {

    private final JTable table;

    public ClearFilterTableUseCase(JTable table) {
        this.table = table;
    }

    @Override
    public void handleEvent(ClearFilterRequestedEvent event) {
        SwingUtilities.invokeLater(() -> {
            TableRowSorter sorter = new TableRowSorter<>(table.getModel());
            sorter.setRowFilter(null);
            table.setRowSorter(sorter);
        });
    }

    @Override
    public List<Class<? extends Event>> subscribedEvents() {
        return Collections.singletonList(ClearFilterRequestedEvent.class);
    }
}
