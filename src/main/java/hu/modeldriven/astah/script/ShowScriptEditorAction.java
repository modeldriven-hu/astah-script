package hu.modeldriven.astah.script;


import com.change_vision.jude.api.inf.ui.IPluginActionDelegate;
import com.change_vision.jude.api.inf.ui.IWindow;
import hu.modeldriven.astah.script.ui.ScriptEditorDialog;

public class ShowScriptEditorAction implements IPluginActionDelegate {

    private final ScriptEditorDialog dialog;

    public ShowScriptEditorAction() {
        dialog = new ScriptEditorDialog(null);
    }

    public Object run(IWindow window) {
        dialog.display();
        return null;
    }

}
