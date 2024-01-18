package hu.modeldriven.astah.script.ui.event;

import hu.modeldriven.astah.script.common.eventbus.Event;
import hu.modeldriven.astah.script.common.history.HistoryRecord;

public class HistoryRecordSelectedEvent implements Event {

    private final HistoryRecord historyRecord;

    public HistoryRecordSelectedEvent(HistoryRecord historyRecord) {
        this.historyRecord = historyRecord;
    }

    public HistoryRecord getHistoryRecord() {
        return historyRecord;
    }
}
