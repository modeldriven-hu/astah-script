package hu.modeldriven.astah.script.common.script;

public interface ScriptExecutor {

    Object execute(String script) throws ScriptExecutionException;

    String getLanguage();

}
