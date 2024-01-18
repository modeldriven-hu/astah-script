package hu.modeldriven.astah.script.ui.usecase;

import hu.modeldriven.astah.script.ui.event.HistoryRecordSelectedEvent;
import hu.modeldriven.core.eventbus.Event;
import hu.modeldriven.core.eventbus.EventHandler;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import javax.swing.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

public class DisplaySelectedHistoryRecordInEditorUseCase implements EventHandler<HistoryRecordSelectedEvent> {

    private final JComboBox comboBox;
    private final RSyntaxTextArea textArea;

    public DisplaySelectedHistoryRecordInEditorUseCase(JComboBox comboBox, RSyntaxTextArea textArea) {
        this.comboBox = comboBox;
        this.textArea = textArea;
    }

    @Override
    public void handleEvent(HistoryRecordSelectedEvent event) {
        SwingUtilities.invokeLater(() -> {
            comboBox.setSelectedItem(event.getHistoryRecord().getLanguage());

            String decodedString = new String(Base64.getDecoder().decode(event.getHistoryRecord().getScript()), StandardCharsets.UTF_8);
            textArea.setText(decodedString);
        });
    }

    @Override
    public List<Class<? extends Event>> subscribedEvents() {
        return Collections.singletonList(HistoryRecordSelectedEvent.class);
    }
}
