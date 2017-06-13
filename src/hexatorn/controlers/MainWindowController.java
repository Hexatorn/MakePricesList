package hexatorn.controlers;

import hexatorn.util.ReadConfigFromXML;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

public class MainWindowController {

    public MainWindowController(){

    }

    @FXML
    private ToolBar toolBarMenu;

    @FXML
    public void initialize() {
        ReadConfigFromXML.getProfileName();

        toolBarMenu.getItems().addAll(new Button("test"));
        //System.out.println(toolBarMenu.getItems());
    }
}
