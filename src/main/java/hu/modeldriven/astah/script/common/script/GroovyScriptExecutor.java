package hu.modeldriven.astah.script.common.script;

import com.change_vision.jude.api.inf.AstahAPI;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;

public class GroovyScriptExecutor implements ScriptExecutor {

    @Override
    public String getLanguage() {
        return "Groovy";
    }

    @Override
    public Object execute(String script) throws ScriptExecutionException {

        try {
            AstahAPI api = AstahAPI.getAstahAPI();

            Binding binding = new Binding();
            binding.setVariable("api", api);
            binding.setVariable("helper", new ScriptHelper());
            GroovyShell shell = new GroovyShell(binding);

            return shell.evaluate(script);
        } catch (Exception ex) {
            throw new GroovyScriptExecutionException(ex);
        }
    }


}
