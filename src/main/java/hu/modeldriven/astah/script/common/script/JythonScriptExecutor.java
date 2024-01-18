package hu.modeldriven.astah.script.common.script;

import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

import java.io.StringWriter;

public class JythonScriptExecutor implements ScriptExecutor {

    @Override
    public String getLanguage() {
        return "Python";
    }

    @Override
    public Object execute(String script) throws ScriptExecutionException {

        StringWriter errorStream = new StringWriter();

        try {
            PythonInterpreter interpreter = new PythonInterpreter();

            interpreter.set("model_helper", new ModelHelper());
            interpreter.setErr(errorStream);
            interpreter.exec(script);

            PyObject result = interpreter.get("result");

            return new PythonResult(result).asJava();

        } catch (Exception e) {
            throw new JythonScriptExecutionException(e);
        }
    }

}
