package hexatorn;

import javafx.fxml.FXML;
import javafx.scene.control.ToolBar;

public class Controller {

    public Controller(){
        System.out.println("first");
    }

    @FXML
    private ToolBar toolBarMenu;

    @FXML
    public void initialize() {
        System.out.println("second");
    }
}
