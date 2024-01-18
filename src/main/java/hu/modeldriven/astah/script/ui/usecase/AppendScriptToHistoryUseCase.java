package hu.modeldriven.astah.script.ui.usecase;

import hu.modeldriven.astah.script.common.eventbus.EventBus;
import hu.modeldriven.astah.script.common.history.HistoryLog;
import hu.modeldriven.astah.script.common.usecase.UseCase;
import hu.modeldriven.astah.script.ui.event.AppendScriptToHistoryRequestedEvent;
import hu.modeldriven.astah.script.ui.event.HistoryChangedEvent;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import javax.swing.*;

public class AppendScriptToHistoryUseCase implements UseCase {

    private final EventBus eventBus;
    private final JComboBox comboBox;
    private final RSyntaxTextArea textArea;
    private final HistoryLog historyLog;

    public AppendScriptToHistoryUseCase(EventBus eventBus, HistoryLog historyLog, JComboBox comboBox, RSyntaxTextArea textArea) {
        this.eventBus = eventBus;
        this.comboBox = comboBox;
        this.textArea = textArea;
        this.historyLog = historyLog;
        eventBus.subscribe(AppendScriptToHistoryRequestedEvent.class, this::appendScriptToHistoryRequested);
    }

    private void appendScriptToHistoryRequested(AppendScriptToHistoryRequestedEvent event) {
        historyLog.append(comboBox.getSelectedItem().toString(), textArea.getText());
        eventBus.publish(new HistoryChangedEvent());
    }
}
