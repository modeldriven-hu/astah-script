package hu.modeldriven.astah.script.ui.usecase;

import hu.modeldriven.astah.script.common.eventbus.EventBus;
import hu.modeldriven.astah.script.common.history.HistoryLog;
import hu.modeldriven.astah.script.common.usecase.UseCase;
import hu.modeldriven.astah.script.ui.event.CloseDialogRequestedEvent;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import javax.swing.*;

public class AppendScriptToHistoryOnCloseUseCase implements UseCase {

    private final HistoryLog historyLog;
    private final JComboBox comboBox;
    private final RSyntaxTextArea textArea;

    public AppendScriptToHistoryOnCloseUseCase(EventBus eventBus, HistoryLog historyLog, JComboBox comboBox, RSyntaxTextArea textArea) {
        this.textArea = textArea;
        this.comboBox = comboBox;
        this.historyLog = historyLog;
        eventBus.subscribe(CloseDialogRequestedEvent.class, this::onCloseDialogRequested);
    }

    private void onCloseDialogRequested(CloseDialogRequestedEvent event) {
        historyLog.append(comboBox.getSelectedItem().toString(), textArea.getText());
    }

}
