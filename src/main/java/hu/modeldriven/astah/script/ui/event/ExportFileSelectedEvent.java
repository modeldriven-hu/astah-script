package hu.modeldriven.astah.script.ui.event;

import java.io.File;

import hu.modeldriven.core.eventbus.Event;

public class ExportFileSelectedEvent implements Event {

    private final File file;

    public ExportFileSelectedEvent(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }
}
