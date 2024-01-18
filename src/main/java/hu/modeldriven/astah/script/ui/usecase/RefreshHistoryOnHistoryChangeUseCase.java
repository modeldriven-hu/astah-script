package hu.modeldriven.astah.script.ui.usecase;

import hu.modeldriven.astah.script.common.eventbus.EventBus;
import hu.modeldriven.astah.script.common.history.HistoryLog;
import hu.modeldriven.astah.script.common.history.HistoryRecord;
import hu.modeldriven.astah.script.common.usecase.UseCase;
import hu.modeldriven.astah.script.ui.event.ExceptionOccurredEvent;
import hu.modeldriven.astah.script.ui.event.HistoryChangedEvent;

import javax.swing.*;
import java.util.List;

@SuppressWarnings({"squid:S3740"})
public class RefreshHistoryOnHistoryChangeUseCase implements UseCase {

    private final EventBus eventBus;
    private final JComboBox comboBox;
    private final HistoryLog historyLog;

    public RefreshHistoryOnHistoryChangeUseCase(EventBus eventBus, HistoryLog historyLog, JComboBox comboBox) {
        this.eventBus = eventBus;
        this.comboBox = comboBox;
        this.historyLog = historyLog;
        eventBus.subscribe(HistoryChangedEvent.class, this::onHistoryChanged);
    }

    @SuppressWarnings("unchecked")
    private void onHistoryChanged(HistoryChangedEvent event) {
        SwingUtilities.invokeLater(() -> {
            try {
                List<HistoryRecord> log = historyLog.read();
                DefaultComboBoxModel model = new DefaultComboBoxModel<>(log.toArray(HistoryRecord[]::new));
                this.comboBox.setModel(model);
            } catch (Exception e) {
                eventBus.publish(new ExceptionOccurredEvent(e));
            }
        });
    }

}
