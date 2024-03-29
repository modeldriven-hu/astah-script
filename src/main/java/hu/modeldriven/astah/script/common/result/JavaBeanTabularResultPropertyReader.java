package hu.modeldriven.astah.script.common.result;

import hu.modeldriven.astah.script.common.reflection.Bean;

public class JavaBeanTabularResultPropertyReader implements TabularResultPropertyReader {

    @Override
    public Object getPropertyByName(Object object, String propertyName) {
        return Bean.of(object).propertyAsString(propertyName);
    }

    @Override
    public Object getPropertyOrEmptyStringByName(Object object, String propertyName) {
        return Bean.of(object).propertyOrEmptyString(propertyName);
    }

    @Override
    public String getPropertyNameAsString(Object object, String propertyName) {
        return getPropertyOrEmptyStringByName(object, propertyName).toString();
    }
}
