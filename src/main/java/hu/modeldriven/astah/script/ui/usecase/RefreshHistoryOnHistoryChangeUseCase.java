package hu.modeldriven.astah.script.ui.usecase;

import hu.modeldriven.astah.script.common.history.HistoryLog;
import hu.modeldriven.astah.script.common.history.HistoryRecord;
import hu.modeldriven.astah.script.ui.event.ExceptionOccurredEvent;
import hu.modeldriven.astah.script.ui.event.HistoryChangedEvent;
import hu.modeldriven.core.eventbus.Event;
import hu.modeldriven.core.eventbus.EventBus;
import hu.modeldriven.core.eventbus.EventHandler;

import javax.swing.*;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"squid:S3740"})
public class RefreshHistoryOnHistoryChangeUseCase implements EventHandler<HistoryChangedEvent> {

    private final EventBus eventBus;
    private final JComboBox comboBox;
    private final HistoryLog historyLog;

    public RefreshHistoryOnHistoryChangeUseCase(EventBus eventBus, HistoryLog historyLog, JComboBox comboBox) {
        this.eventBus = eventBus;
        this.comboBox = comboBox;
        this.historyLog = historyLog;
    }

    @Override
    public void handleEvent(HistoryChangedEvent event) {
        SwingUtilities.invokeLater(() -> {
            try {
                List<HistoryRecord> log = historyLog.read();
                DefaultComboBoxModel model = new DefaultComboBoxModel<>(log.toArray(new HistoryRecord[0]));
                this.comboBox.setModel(model);
            } catch (Exception e) {
                eventBus.publish(new ExceptionOccurredEvent(e));
            }
        });
    }

    @Override
    public List<Class<? extends Event>> subscribedEvents() {
        return Collections.singletonList(HistoryChangedEvent.class);
    }
}
