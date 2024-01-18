package hu.modeldriven.astah.script.common.script;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

public class GroovyScriptExecutor implements ScriptExecutor {

    @Override
    public String getLanguage() {
        return "Groovy";
    }

    @Override
    public Object execute(String script) throws ScriptExecutionException {

        Binding binding = new Binding();
        binding.setVariable("modelHelper", new ModelHelper());
        GroovyShell shell = new GroovyShell(binding);

        try {
            return shell.evaluate(script);
        } catch (Exception ex) {
            throw new GroovyScriptExecutionException(ex);
        }
    }

}
