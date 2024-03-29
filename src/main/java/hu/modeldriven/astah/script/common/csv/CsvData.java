package hu.modeldriven.astah.script.common.csv;

import hu.modeldriven.astah.script.common.result.TabularResult;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("squid:S3878")
public class CsvData {

    private final TabularResult result;

    public CsvData(TabularResult result) {
        this.result = result;
    }

    public List<String[]> asRawData() {
        Stream<String[]> headerStream = Arrays.asList(
                new String[][]{result.getFields().values().toArray(new String[0])}
        ).stream();

        Stream<String[]> dataStream =
                result.getList().stream()
                        .map(obj ->
                                result.getFields()
                                        .keySet()
                                        .stream()
                                        .map(k -> result.getPropertyReader().getPropertyNameAsString(obj, k))
                                        .toArray(String[]::new));

        return Stream.concat(headerStream, dataStream).collect(Collectors.toList());
    }

}
