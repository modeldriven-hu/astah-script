package hu.modeldriven.astah.script.common.result;

public interface TabularResultPropertyReader {

    Object getPropertyByName(Object object, String propertyName);

    Object getPropertyOrEmptyStringByName(Object object, String propertyName);

    String getPropertyNameAsString(Object object, String propertyName);

}
