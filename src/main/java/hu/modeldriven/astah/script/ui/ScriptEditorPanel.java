package hu.modeldriven.astah.script.ui;

import hu.modeldriven.astah.script.common.annotation.OnEventDispatcherThread;
import hu.modeldriven.astah.script.common.eventbus.EventBus;
import hu.modeldriven.astah.script.common.history.HistoryComboBoxRenderer;
import hu.modeldriven.astah.script.common.history.HistoryLog;
import hu.modeldriven.astah.script.common.history.HistoryRecord;
import hu.modeldriven.astah.script.common.script.GroovyScriptExecutor;
import hu.modeldriven.astah.script.common.script.JythonScriptExecutor;
import hu.modeldriven.astah.script.common.script.ScriptExecutor;
import hu.modeldriven.astah.script.common.storage.LocalStorage;
import hu.modeldriven.astah.script.common.ui.KeyPressedForwarder;
import hu.modeldriven.astah.script.common.usecase.UseCase;
import hu.modeldriven.astah.script.ui.event.*;
import hu.modeldriven.astah.script.ui.usecase.*;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings({"squid:S1068", "squid:S1948"})
public class ScriptEditorPanel extends BaseScriptEditorPanel {

    private final EventBus eventBus;
    private final List<ScriptExecutor> executors;
    private final HistoryLog historyLog;

    private final UseCase[] useCases;

    public ScriptEditorPanel(EventBus eventBus) {
        super();
        this.eventBus = eventBus;
        this.historyLog = new HistoryLog(eventBus, new LocalStorage());
        this.executors = Arrays.asList(new GroovyScriptExecutor(), new JythonScriptExecutor());

        this.updateComponents();

        useCases = new UseCase[]{
                new AppendScriptToHistoryOnCloseUseCase(eventBus, this.historyLog, this.languageComboBox, this.scriptTextArea),
                new AppendScriptToHistoryUseCase(eventBus, this.historyLog, this.languageComboBox, this.scriptTextArea),
                new ClearFilterTableUseCase(eventBus, this.outputTable),
                new CsvExportUseCase(eventBus),
                new DisplayEmptyResultInOutputTextAreaUseCase(eventBus, this.outputTextArea),
                new DisplayExceptionInOutputTextAreaUseCase(eventBus, this.outputTextArea),
                new DisplayListResultInTableUseCase(eventBus, this.outputTable),
                new DisplayScriptExecutionFailedInOutputTextAreaUseCase(eventBus, this.outputTextArea),
                new DisplaySelectedHistoryRecordInEditorUseCase(eventBus, this.languageComboBox, this.scriptTextArea),
                new DisplayTabularResultInTableUseCase(eventBus, this.outputTable),
                new DisplayStringResultInOutputTextAreaUseCase(eventBus, this.outputTextArea),
                new ExecuteSelectedScriptUseCase(eventBus, executors),
                new FilterTableUseCase(eventBus, this.outputTable),
                new FocusOnResultGridWhenRequestedUseCase(eventBus, this.resultTabbedPane),
                new FocusOnResultTextAreaWhenRequestedUseCase(eventBus, this.resultTabbedPane),
                new LoadHistoryOnDialogDisplayUseCase(eventBus, this.historyLog, this.historyComboBox),
                new RefreshHistoryOnHistoryChangeUseCase(eventBus, this.historyLog, this.historyComboBox),
                new SelectErroneousLineInEditorUseCase(eventBus, this.scriptTextArea)};

        eventBus.publish(new DialogDisplayedEvent());
    }

    @SuppressWarnings("unchecked")
    @OnEventDispatcherThread
    private void updateComponents() {

        this.scriptTextArea.setCodeFoldingEnabled(true);
        this.scriptTextArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_GROOVY);
        this.scriptTextArea.setAntiAliasingEnabled(true);

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

    @OnEventDispatcherThread
    private void selectHistoryCommand(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            eventBus.publish(new HistoryRecordSelectedEvent((HistoryRecord) e.getItem()));
        }
    }

    @OnEventDispatcherThread
    private void appendToHistoryCommand(ActionEvent e) {
        eventBus.publish(new AppendScriptToHistoryRequestedEvent());
    }

    @OnEventDispatcherThread
    private void closeDialogCommand(ActionEvent e) {
        eventBus.publish(new CloseDialogRequestedEvent());
    }

    @OnEventDispatcherThread
    private void executeScriptCommand(ActionEvent e) {
        eventBus.publish(new ScriptExecutionRequestedEvent(languageComboBox.getSelectedItem().toString(), scriptTextArea.getText()));
    }

    @OnEventDispatcherThread
    private void exportCsvCommand(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();

        if (JFileChooser.APPROVE_OPTION == fileChooser.showSaveDialog(null)) {
            eventBus.publish(new ExportFileSelectedEvent(fileChooser.getSelectedFile()));
        }
    }

}
