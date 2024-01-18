package hu.modeldriven.astah.script.ui.usecase;

import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import hu.modeldriven.astah.script.common.csv.CsvData;
import hu.modeldriven.astah.script.common.result.TabularResult;
import hu.modeldriven.astah.script.ui.event.ExceptionOccurredEvent;
import hu.modeldriven.astah.script.ui.event.ExportFileSelectedEvent;
import hu.modeldriven.astah.script.ui.event.TabularResultCreatedEvent;
import hu.modeldriven.core.eventbus.Event;
import hu.modeldriven.core.eventbus.EventBus;
import hu.modeldriven.core.eventbus.EventHandler;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CsvExportUseCase implements EventHandler<Event> {

    private final EventBus eventBus;

    private Optional<TabularResult> result = Optional.empty();

    public CsvExportUseCase(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void handleEvent(Event event) {

        if (event instanceof TabularResultCreatedEvent) {
            onSmartGridResultCreated((TabularResultCreatedEvent) event);
        }

        if (event instanceof ExportFileSelectedEvent) {
            onExportFileSelect((ExportFileSelectedEvent) event);
        }
    }

    public void onSmartGridResultCreated(TabularResultCreatedEvent event) {
        this.result = Optional.of(event.getTabularResult());
    }

    public void onExportFileSelect(ExportFileSelectedEvent event) {
        result.ifPresent(result -> {
            try (ICSVWriter writer = new CSVWriterBuilder(new FileWriter(event.getFile())).withSeparator(';').build()) {
                List<String[]> data = new CsvData(result).asRawData();
                writer.writeAll(data);
            } catch (IOException e) {
                eventBus.publish(new ExceptionOccurredEvent(e));
            }
        });
    }

    @Override
    public List<Class<? extends Event>> subscribedEvents() {
        return Arrays.asList(TabularResultCreatedEvent.class, ExportFileSelectedEvent.class);
    }
}
