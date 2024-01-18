package hu.modeldriven.astah.script.ui.usecase;

import java.util.Collections;
import java.util.List;

import javax.swing.JComboBox;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import hu.modeldriven.astah.script.common.history.HistoryLog;
import hu.modeldriven.astah.script.ui.event.CloseDialogRequestedEvent;
import hu.modeldriven.core.eventbus.Event;
import hu.modeldriven.core.eventbus.EventBus;
import hu.modeldriven.core.eventbus.EventHandler;

public class AppendScriptToHistoryOnCloseUseCase implements EventHandler<CloseDialogRequestedEvent>{

    private final HistoryLog historyLog;
    private final JComboBox<String> comboBox;
    private final RSyntaxTextArea textArea;

    public AppendScriptToHistoryOnCloseUseCase(EventBus eventBus, HistoryLog historyLog, JComboBox<String> comboBox, RSyntaxTextArea textArea) {
        this.textArea = textArea;
        this.comboBox = comboBox;
        this.historyLog = historyLog;
    }

	@Override
	public void handleEvent(CloseDialogRequestedEvent event) {
        historyLog.append(comboBox.getSelectedItem().toString(), textArea.getText());		
	}

	@Override
	public List<Class<? extends Event>> subscribedEvents() {
		return Collections.singletonList(CloseDialogRequestedEvent.class);
	}

}
