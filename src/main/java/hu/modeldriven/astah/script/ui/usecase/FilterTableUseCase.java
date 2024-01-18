package hu.modeldriven.astah.script.ui.usecase;

import hu.modeldriven.astah.script.common.eventbus.EventBus;
import hu.modeldriven.astah.script.common.usecase.UseCase;
import hu.modeldriven.astah.script.ui.event.FilterRequestedEvent;

import javax.swing.*;
import javax.swing.table.TableRowSorter;

public class FilterTableUseCase implements UseCase {

    private final JTable table;

    public FilterTableUseCase(EventBus eventBus, JTable table) {
        this.table = table;
        eventBus.subscribe(FilterRequestedEvent.class, this::filterRequested);
    }

    private void filterRequested(FilterRequestedEvent event) {

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

}
