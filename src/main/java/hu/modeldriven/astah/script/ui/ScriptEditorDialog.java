package hu.modeldriven.astah.script.ui;

import hu.modeldriven.astah.script.ui.event.CloseDialogRequestedEvent;
import hu.modeldriven.core.eventbus.Event;
import hu.modeldriven.core.eventbus.EventBus;
import hu.modeldriven.core.eventbus.EventHandler;

import javax.swing.*;
import java.awt.Frame;
import java.util.Collections;
import java.util.List;

public class ScriptEditorDialog extends JDialog implements EventHandler<CloseDialogRequestedEvent> {

    private final transient EventBus eventBus;

    public ScriptEditorDialog(Frame parent) {
        super(parent, "Script editor", false);

        this.eventBus = new EventBus();
        eventBus.subscribe(this);

        this.setContentPane(new ScriptEditorPanel(eventBus));
        this.pack();
    }

    @Override
    public void handleEvent(CloseDialogRequestedEvent event) {
        ScriptEditorDialog.this.dispose();
    }

    @Override
    public List<Class<? extends Event>> subscribedEvents() {
        return Collections.singletonList(CloseDialogRequestedEvent.class);
    }
}
