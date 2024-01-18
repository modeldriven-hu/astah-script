package hu.modeldriven.astah.script.ui.usecase;

import hu.modeldriven.astah.script.common.history.HistoryLog;
import hu.modeldriven.astah.script.ui.event.AppendScriptToHistoryRequestedEvent;
import hu.modeldriven.astah.script.ui.event.HistoryChangedEvent;
import hu.modeldriven.core.eventbus.Event;
import hu.modeldriven.core.eventbus.EventBus;
import hu.modeldriven.core.eventbus.EventHandler;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import javax.swing.*;
import java.util.Collections;
import java.util.List;

public class AppendScriptToHistoryUseCase implements EventHandler<AppendScriptToHistoryRequestedEvent> {

    private final EventBus eventBus;
    private final JComboBox comboBox;
    private final RSyntaxTextArea textArea;
    private final HistoryLog historyLog;

    public AppendScriptToHistoryUseCase(EventBus eventBus, HistoryLog historyLog, JComboBox comboBox, RSyntaxTextArea textArea) {
        this.eventBus = eventBus;
        this.comboBox = comboBox;
        this.textArea = textArea;
        this.historyLog = historyLog;
    }


    @Override
    public void handleEvent(AppendScriptToHistoryRequestedEvent event) {
        historyLog.append(comboBox.getSelectedItem().toString(), textArea.getText());
        eventBus.publish(new HistoryChangedEvent());
    }

    @Override
    public List<Class<? extends Event>> subscribedEvents() {
        return Collections.singletonList(AppendScriptToHistoryRequestedEvent.class);
    }
}
