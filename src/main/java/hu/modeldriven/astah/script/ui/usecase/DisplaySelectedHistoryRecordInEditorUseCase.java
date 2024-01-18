package hu.modeldriven.astah.script.ui.usecase;

import hu.modeldriven.astah.script.common.eventbus.EventBus;
import hu.modeldriven.astah.script.common.usecase.UseCase;
import hu.modeldriven.astah.script.ui.event.HistoryRecordSelectedEvent;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import javax.swing.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class DisplaySelectedHistoryRecordInEditorUseCase implements UseCase {

    private final JComboBox comboBox;
    private final RSyntaxTextArea textArea;

    public DisplaySelectedHistoryRecordInEditorUseCase(EventBus eventBus, JComboBox comboBox, RSyntaxTextArea textArea) {
        this.comboBox = comboBox;
        this.textArea = textArea;
        eventBus.subscribe(HistoryRecordSelectedEvent.class, this::onHistoryRecordSelected);
    }

    private void onHistoryRecordSelected(HistoryRecordSelectedEvent event) {

        SwingUtilities.invokeLater(() -> {
            comboBox.setSelectedItem(event.getHistoryRecord().getLanguage());

            String decodedString = new String(Base64.getDecoder().decode(event.getHistoryRecord().getScript()), StandardCharsets.UTF_8);
            textArea.setText(decodedString);
        });
    }

}
