package hu.modeldriven.astah.script.ui.event;

import hu.modeldriven.astah.script.common.history.HistoryRecord;
import hu.modeldriven.core.eventbus.Event;

public class HistoryRecordSelectedEvent implements Event {

    private final HistoryRecord historyRecord;

    public HistoryRecordSelectedEvent(HistoryRecord historyRecord) {
        this.historyRecord = historyRecord;
    }

    public HistoryRecord getHistoryRecord() {
        return historyRecord;
    }
}
