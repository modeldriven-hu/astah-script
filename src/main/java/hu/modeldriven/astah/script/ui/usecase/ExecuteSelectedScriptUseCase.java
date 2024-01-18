package hu.modeldriven.astah.script.ui.usecase;

import hu.modeldriven.astah.script.common.result.CastedList;
import hu.modeldriven.astah.script.common.result.ListResult;
import hu.modeldriven.astah.script.common.result.TabularResult;
import hu.modeldriven.astah.script.common.script.ExecutorNotFoundException;
import hu.modeldriven.astah.script.common.script.ScriptExecutionException;
import hu.modeldriven.astah.script.common.script.ScriptExecutor;
import hu.modeldriven.astah.script.ui.event.*;
import hu.modeldriven.core.eventbus.Event;
import hu.modeldriven.core.eventbus.EventBus;
import hu.modeldriven.core.eventbus.EventHandler;

import java.util.Collections;
import java.util.List;

public class ExecuteSelectedScriptUseCase implements EventHandler<ScriptExecutionRequestedEvent> {

    private final EventBus eventBus;
    private final List<ScriptExecutor> executors;

    public ExecuteSelectedScriptUseCase(EventBus eventBus, List<ScriptExecutor> executors) {
        this.eventBus = eventBus;
        this.executors = executors;
    }

    @Override
    public void handleEvent(ScriptExecutionRequestedEvent event) {
        try {

            String language = event.getLanguage();

            ScriptExecutor executor = executors
                    .stream()
                    .filter(e -> e.getLanguage().equals(event.getLanguage()))
                    .findFirst()
                    .orElseThrow(ExecutorNotFoundException::new);

            Object value = executor.execute(event.getScript());

            if (value == null) {
                eventBus.publish(new EmptyResultCreatedEvent());
            } else if (value instanceof ListResult) {
                eventBus.publish(new ListResultCreatedEvent(((ListResult) value).asList()));
            } else if (value instanceof TabularResult) {
                eventBus.publish(new TabularResultCreatedEvent((TabularResult) value));
            } else if (value instanceof Iterable || isArray(value)) {
                CastedList.of(value).ifPresent(list -> eventBus.publish(new ListResultCreatedEvent(list.asList())));
            } else {
                eventBus.publish(new StringResultCreatedEvent(value.toString()));
            }

        } catch (ScriptExecutionException e) {
            eventBus.publish(new ScriptExecutionFailedEvent(e));
        } catch (ExecutorNotFoundException e) {
            eventBus.publish(new ExecutorNotFoundEvent(e));
        } catch (Exception e) {
            eventBus.publish(new ExceptionOccurredEvent(e));
        }
    }

    private boolean isArray(Object array) {
        return array != null && (array.getClass().isArray());
    }


    @Override
    public List<Class<? extends Event>> subscribedEvents() {
        return Collections.singletonList(ScriptExecutionRequestedEvent.class);
    }
}
