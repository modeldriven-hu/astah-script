package hu.modeldriven.astah.script.ui.usecase;

import hu.modeldriven.astah.script.ui.event.FilterRequestedEvent;
import hu.modeldriven.core.eventbus.Event;
import hu.modeldriven.core.eventbus.EventHandler;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.util.Collections;
import java.util.List;

public class FilterTableUseCase implements EventHandler<FilterRequestedEvent> {

    private final JTable table;

    public FilterTableUseCase(JTable table) {
        this.table = table;
    }

    @Override
    public void handleEvent(FilterRequestedEvent event) {
        SwingUtilities.invokeLater(() -> {

            TableRowSorter sorter = new TableRowSorter<>(table.getModel());

            try {
                RowFilter rf = RowFilter.regexFilter(event.getFilter());
                sorter.setRowFilter(rf);
            } catch (Exception e) {
                return;
            }

            table.setRowSorter(sorter);
        });
    }

    @Override
    public List<Class<? extends Event>> subscribedEvents() {
        return Collections.singletonList(FilterRequestedEvent.class);
    }
}
