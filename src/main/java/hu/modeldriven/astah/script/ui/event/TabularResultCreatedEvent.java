package hu.modeldriven.astah.script.ui.event;

import hu.modeldriven.astah.script.common.result.TabularResult;
import hu.modeldriven.core.eventbus.Event;

public class TabularResultCreatedEvent implements Event {

    TabularResult result;

    public TabularResultCreatedEvent(TabularResult result) {
        this.result = result;
    }

    public TabularResult getTabularResult() {
        return result;
    }

}
