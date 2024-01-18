package hu.modeldriven.astah.script;


import com.change_vision.jude.api.inf.ui.IPluginActionDelegate;
import com.change_vision.jude.api.inf.ui.IWindow;
import hu.modeldriven.astah.script.ui.ScriptEditorDialog;

public class ShowScriptEditorAction implements IPluginActionDelegate {

    public Object run(IWindow window) throws UnExpectedException {

        ScriptEditorDialog dialog = new ScriptEditorDialog(null);
        dialog.setVisible(true);

        return null;
    }


}
