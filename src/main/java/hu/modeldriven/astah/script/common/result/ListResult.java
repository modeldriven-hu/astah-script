package hu.modeldriven.astah.script.common.result;

import java.util.Arrays;
import java.util.List;

public class ListResult {

    private final List<Object> list;

    public ListResult(List<Object> list) {
        this.list = list;
    }

    public ListResult(Object[] array) {
        this.list = Arrays.asList(array);
    }

    public List<Object> asList() {
        return list;
    }
}
