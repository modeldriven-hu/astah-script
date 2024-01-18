package hu.modeldriven.astah.script.ui.usecase;

import hu.modeldriven.astah.script.common.eventbus.EventBus;
import hu.modeldriven.astah.script.common.history.HistoryLog;
import hu.modeldriven.astah.script.common.history.HistoryRecord;
import hu.modeldriven.astah.script.common.usecase.UseCase;
import hu.modeldriven.astah.script.ui.event.DialogDisplayedEvent;
import hu.modeldriven.astah.script.ui.event.ExceptionOccurredEvent;

import javax.swing.*;
import java.util.List;

@SuppressWarnings({"squid:S3740"})
public class LoadHistoryOnDialogDisplayUseCase implements UseCase {

    private final EventBus eventBus;
    private final JComboBox comboBox;
    private final HistoryLog historyLog;

    public LoadHistoryOnDialogDisplayUseCase(EventBus eventBus, HistoryLog historyLog, JComboBox comboBox) {
        this.eventBus = eventBus;
        this.comboBox = comboBox;
        this.historyLog = historyLog;
        eventBus.subscribe(DialogDisplayedEvent.class, this::onDialogDisplayed);
    }

    @SuppressWarnings("unchecked")
    private void onDialogDisplayed(DialogDisplayedEvent event) {

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
