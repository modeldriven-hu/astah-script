package hu.modeldriven.astah.script.common.history;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import hu.modeldriven.astah.script.common.eventbus.EventBus;
import hu.modeldriven.astah.script.common.storage.LocalStorage;
import hu.modeldriven.astah.script.ui.event.ExceptionOccurredEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class HistoryLog {

    private static final String HISTORY_JSON = "history.json";

    private final EventBus eventBus;
    private final LocalStorage localStorage;

    public HistoryLog(EventBus eventBus, LocalStorage storage) {
        this.localStorage = storage;
        this.eventBus = eventBus;
    }

    public List<HistoryRecord> read() throws FileNotFoundException {

        List<HistoryRecord> retValue = Collections.emptyList();

        File file = localStorage.getOrCreate(HISTORY_JSON);

        if (file.length() > 0) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            retValue = Arrays.asList(gson.fromJson(new JsonReader(new FileReader(file)), HistoryRecord[].class));
        }

        return retValue;
    }

    public void append(String language, String script) {
        String base64EncodedScript = Base64.getEncoder().withoutPadding().encodeToString(script.getBytes(StandardCharsets.UTF_8));

        File file = localStorage.getOrCreate(HISTORY_JSON);

        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            List<HistoryRecord> list = new ArrayList<>();

            if (file.length() != 0) {
                list.addAll(Arrays.asList(gson.fromJson(new JsonReader(new FileReader(file)), HistoryRecord[].class)));
            }

            list.add(new HistoryRecord(language, base64EncodedScript));

            try (FileWriter fileWriter = new FileWriter(file)) {
                gson.toJson(list, fileWriter);
                fileWriter.flush();
            }

        } catch (Exception e) {
            eventBus.publish(new ExceptionOccurredEvent(e));
        }
    }

}
