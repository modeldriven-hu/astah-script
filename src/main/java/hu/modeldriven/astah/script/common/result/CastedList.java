package hu.modeldriven.astah.script.common.result;

import java.util.*;

public class CastedList {

    private final Object obj;

    private CastedList(Object obj) {
        this.obj = obj;
    }

    public static Optional<CastedList> of(Object obj) {
        if ((isArray(obj) || isIterable(obj))) {
            return Optional.of(new CastedList(obj));
        }

        return Optional.empty();
    }

    private static boolean isArray(Object array) {
        return array != null && (array.getClass().isArray());
    }

    private static boolean isIterable(Object iterable) {
        return iterable instanceof Iterable;
    }

    public List<Object> asList() {

        if (obj instanceof Object[]) {
            return Arrays.asList((Object[]) obj);
        }

        if (obj instanceof boolean[]) {
            return Collections.singletonList(obj);
        }

        if (obj instanceof byte[]) {
            return Collections.singletonList(obj);
        }

        if (obj instanceof short[]) {
            return Collections.singletonList(obj);
        }

        if (obj instanceof char[]) {
            return Collections.singletonList(obj);
        }

        if (obj instanceof int[]) {
            return Collections.singletonList(obj);
        }

        if (obj instanceof long[]) {
            return Collections.singletonList(obj);
        }

        if (obj instanceof float[]) {
            return Collections.singletonList(obj);
        }

        if (obj instanceof double[]) {
            return Collections.singletonList(obj);
        }

        if (obj instanceof Iterable) {
            @SuppressWarnings("unchecked") Iterable<Object> iterable = (Iterable<Object>) obj;
            List<Object> retValue = new ArrayList<>();
            iterable.forEach(retValue::add);
            return retValue;
        }

        return Collections.singletonList("<<Array type not supported [" + obj.getClass().getComponentType().getName() + "]>>");
    }
}
