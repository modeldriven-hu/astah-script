package hu.modeldriven.astah.script.ui;

import hu.modeldriven.astah.script.common.history.HistoryComboBoxRenderer;
import hu.modeldriven.astah.script.common.history.HistoryLog;
import hu.modeldriven.astah.script.common.history.HistoryRecord;
import hu.modeldriven.astah.script.common.script.GroovyScriptExecutor;
import hu.modeldriven.astah.script.common.script.ScriptExecutor;
import hu.modeldriven.astah.script.common.storage.LocalStorage;
import hu.modeldriven.astah.script.common.ui.KeyPressedForwarder;
import hu.modeldriven.astah.script.ui.event.*;
import hu.modeldriven.astah.script.ui.usecase.*;
import hu.modeldriven.core.eventbus.EventBus;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;

import javax.swing.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"squid:S1068", "squid:S1948"})
public class ScriptEditorPanel extends BaseScriptEditorPanel {

    private final EventBus eventBus;
    private final List<ScriptExecutor> executors;
    private final HistoryLog historyLog;

    public ScriptEditorPanel(EventBus eventBus) {
        super();
        this.eventBus = eventBus;
        this.historyLog = new HistoryLog(eventBus, new LocalStorage());
        this.executors = Collections.singletonList(new GroovyScriptExecutor());

        this.updateComponents();
        this.initUseCases();
    }

    private void initUseCases() {

        eventBus.subscribe(new AppendScriptToHistoryOnCloseUseCase(eventBus, this.historyLog, this.languageComboBox, this.scriptTextArea));
        eventBus.subscribe(new AppendScriptToHistoryUseCase(eventBus, this.historyLog, this.languageComboBox, this.scriptTextArea));
        eventBus.subscribe(new ClearFilterTableUseCase(this.outputTable));
        eventBus.subscribe(new CsvExportUseCase(eventBus));
        eventBus.subscribe(new DisplayEmptyResultInOutputTextAreaUseCase(eventBus, this.outputTextArea));
        eventBus.subscribe(new DisplayExceptionInOutputTextAreaUseCase(eventBus, this.outputTextArea));
        eventBus.subscribe(new DisplayListResultInTableUseCase(eventBus, this.outputTable));
        eventBus.subscribe(new DisplayScriptExecutionFailedInOutputTextAreaUseCase(eventBus, this.outputTextArea));
        eventBus.subscribe(new DisplaySelectedHistoryRecordInEditorUseCase(this.languageComboBox, this.scriptTextArea));
        eventBus.subscribe(new DisplayTabularResultInTableUseCase(eventBus, this.outputTable));
        eventBus.subscribe(new DisplayStringResultInOutputTextAreaUseCase(eventBus, this.outputTextArea));
        eventBus.subscribe(new ExecuteSelectedScriptUseCase(eventBus, executors));
        eventBus.subscribe(new FilterTableUseCase(this.outputTable));
        eventBus.subscribe(new FocusOnResultGridWhenRequestedUseCase(this.resultTabbedPane));
        eventBus.subscribe(new FocusOnResultTextAreaWhenRequestedUseCase(this.resultTabbedPane));
        eventBus.subscribe(new LoadHistoryOnDialogDisplayUseCase(eventBus, this.historyLog, this.historyComboBox));
        eventBus.subscribe(new RefreshHistoryOnHistoryChangeUseCase(eventBus, this.historyLog, this.historyComboBox));
        eventBus.subscribe(new SelectErroneousLineInEditorUseCase(eventBus, this.scriptTextArea));

        eventBus.publish(new DialogDisplayedEvent());
    }

    private void updateComponents() {

        this.scriptTextArea.setCodeFoldingEnabled(true);
        this.scriptTextArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_GROOVY);
        this.scriptTextArea.setAntiAliasingEnabled(true);
        this.scriptTextArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));

        this.scriptTextScrollPane.setLineNumbersEnabled(true);

        for (ScriptExecutor executor : this.executors) {
            this.languageComboBox.addItem(executor.getLanguage());
        }

        this.historyComboBox.setRenderer(new HistoryComboBoxRenderer());

        this.tableFilterTextField.addKeyListener(new KeyPressedForwarder(this::filterTableCommand));
        this.historyComboBox.addItemListener(this::selectHistoryCommand);
        this.appendToHistoryButton.addActionListener(this::appendToHistoryCommand);
        this.closeButton.addActionListener(this::closeDialogCommand);
        this.executeButton.addActionListener(this::executeScriptCommand);
        this.exportButton.addActionListener(this::exportCsvCommand);
    }

    private void filterTableCommand(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {

            if (tableFilterTextField.getText().equals("")) {
                eventBus.publish(new ClearFilterRequestedEvent());
            } else {
                eventBus.publish(new FilterRequestedEvent(tableFilterTextField.getText()));
            }
        }
    }

    private void selectHistoryCommand(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            eventBus.publish(new HistoryRecordSelectedEvent((HistoryRecord) e.getItem()));
        }
    }

    private void appendToHistoryCommand(ActionEvent e) {
        eventBus.publish(new AppendScriptToHistoryRequestedEvent());
    }

    private void closeDialogCommand(ActionEvent e) {
        eventBus.publish(new CloseDialogRequestedEvent());
    }

    private void executeScriptCommand(ActionEvent e) {
        eventBus.publish(new ScriptExecutionRequestedEvent(languageComboBox.getSelectedItem().toString(), scriptTextArea.getText()));
    }

    private void exportCsvCommand(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();

        if (JFileChooser.APPROVE_OPTION == fileChooser.showSaveDialog(null)) {
            eventBus.publish(new ExportFileSelectedEvent(fileChooser.getSelectedFile()));
        }
    }

}
