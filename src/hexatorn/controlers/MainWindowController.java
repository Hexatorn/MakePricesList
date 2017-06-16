package hexatorn.controlers;
import hexatorn.util.ReadConfigFromXML;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.ArrayList;

public class MainWindowController {

    public MainWindowController(){

    }

    @FXML
    private ToolBar toolBarMenu;

    @FXML
    private BorderPane centerPane;
    @FXML
    private BorderPane mainPane;
    @FXML
    private BorderPane menuPane;
    @FXML
    private VBox vBoxColumnId;
    @FXML
    private VBox vBoxOutColumnName;
    @FXML
    private VBox vBoxInColumnName;
    @FXML
    private ScrollPane scrolPane;
    @FXML
    private TextField  tfProfilName;
    @FXML
    private TextField  tfFilePath;
    @FXML
    private TextField  tfSeparator;
    @FXML
    private BorderPane filePane1;
    @FXML
    private VBox vBoxtest;

    private void fillTextField(String s){

        tfProfilName.setText(s);
        System.out.println(ReadConfigFromXML.getConfigFieldsValue(s));
    }
    @FXML
    public void onActionAddNewField(){
        int i = vBoxColumnId.getChildren().size();
        Label lbl = new Label(""+i);
        lbl.setPrefHeight(25);
        vBoxColumnId.getChildren().add(lbl);
        vBoxOutColumnName.getChildren().add(new TextField());
        vBoxInColumnName.getChildren().add(new TextField());
    }

    @FXML
    public void onActionAddRemoveField(){
        int i = vBoxColumnId.getChildren().size()-1;
        if(i>=2){
            vBoxColumnId.getChildren().remove(i);
            vBoxOutColumnName.getChildren().remove(i);
            vBoxInColumnName.getChildren().remove(i);
        }
        else {
            ((TextField) vBoxOutColumnName.getChildren().get(i)).setText("");
            ((TextField) vBoxInColumnName.getChildren().get(i)).setText("");
        }
    }
    public void initialize() {
        createMenu();
        System.out.println(ReadConfigFromXML.getConfigFieldsValue("UK"));
    }
    private void createMenu() {
        ArrayList<String> btnName = ReadConfigFromXML.getProfileName();
        for (String s :btnName) {
            Button btn = new Button("Cennik "+s);
            btn.setOnAction(event -> fillTextField(s));
            toolBarMenu.getItems().add(btn);
        }
        Object[] objects = toolBarMenu.getItems().toArray();
        double with = 90;
        for (Object o : objects) {
            String tmpstr = ((Button) o).getText();
            int tmp = tmpstr.length();
            if(tmp*6>with)
                with = tmp*7;
        }
        for (Object o : objects) {
            ((Button) o).setMinWidth(with);
            ((Button) o).setMaxWidth(with);
            toolBarMenu.setMinWidth(with+10);
            toolBarMenu.setMaxWidth(with+10);
        }
        Node node = toolBarMenu.getItems().get(0);
        toolBarMenu.getItems().remove(0);
        toolBarMenu.getItems().add(node);
    }
}
