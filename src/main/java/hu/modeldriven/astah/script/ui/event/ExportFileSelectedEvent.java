package hu.modeldriven.astah.script.ui.event;

import hu.modeldriven.astah.script.common.eventbus.Event;

import java.io.File;

public class ExportFileSelectedEvent implements Event {

    private final File file;

    public ExportFileSelectedEvent(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }
}
