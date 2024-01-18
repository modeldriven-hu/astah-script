package hu.modeldriven.astah.script.common.script;

import hu.modeldriven.astah.script.common.result.CastedList;
import hu.modeldriven.astah.script.common.result.ListResult;
import hu.modeldriven.astah.script.common.result.TabularResult;

import java.util.List;
import java.util.Map;

/**
 * This class provides helper methods used from the script dialog.
 */
public class ScriptHelper {

    public ListResult listResult(List<Object> elements) {
        return new ListResult(elements);
    }

    public ListResult listResult(Object[] array) {
        return new ListResult(array);
    }

    public TabularResult tabularResult(Object list, Map<String, String> fields) {
        return new TabularResult(CastedList.of(list).orElseThrow(NullPointerException::new).asList(), fields);
    }

}
