package hexatorn.controlers;

import hexatorn.util.ReadConfigFromXML;
import hexatorn.util.ReadXLSXFile;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Map;

public class MainWindowController {

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
    private TextField tfInFilePath;
    @FXML
    private TextField tfOutFilePath;
    @FXML
    private TextField  tfHeadersLine;
    @FXML
    private BorderPane filePane1;

    public MainWindowController(){

    }

    @FXML
    private void onActionSave(){
        System.out.println("click");
        ArrayList<String> arrayList = new ArrayList<>();
        boolean firstElement = true;
        for (Node node : vBoxInColumnName.getChildren()) {
            if (firstElement){ //pominiecie pierwszego element - label
                firstElement = false;
                continue;
            }else if(((TextField) node).getText() == null)
                break;
            else
                arrayList.add(((TextField) node).getText());
        }
        String sHeadersLine = tfHeadersLine.getText();
        int iHeadersLine = Integer.parseInt(sHeadersLine);
        ReadXLSXFile.readData(arrayList, iHeadersLine);
    }


    @FXML
    private void onActionClearField(){
        tfProfilName.setText(null);
        tfInFilePath.setText(null);
        tfOutFilePath.setText(null);
        tfHeadersLine.setText(null);
        for (int i = 2;; i++) {
            try{
                vBoxInColumnName.getChildren().remove(2);
                vBoxOutColumnName.getChildren().remove(2);
                vBoxColumnId.getChildren().remove(2);
            }
            catch (IndexOutOfBoundsException e){
                ((TextField) vBoxInColumnName.getChildren().get(1)).setText(null);
                ((TextField) vBoxOutColumnName.getChildren().get(1)).setText(null);
                break;
            }
        }
    }


    private void fillTextField(String s){
        Map<String,String> map = ReadConfigFromXML.getConfigFieldsValue(s);
        tfProfilName.setText(map.get("ProfileName"));
        tfInFilePath.setText(map.get("DefaultInputFilePath"));
        tfHeadersLine.setText(map.get("InputNumberOfHeaderLines"));
        tfOutFilePath.setText(map.get("OutputDefaultFIlePath"));
        for (int i = 2;; i++) {
            try{
                vBoxInColumnName.getChildren().remove(2);
                vBoxOutColumnName.getChildren().remove(2);
                vBoxColumnId.getChildren().remove(2);
            }
            catch (IndexOutOfBoundsException e){
                break;
            }
        }
        for (int i = 0;; i++) {
            Node node;
            String s1,s2;
            node = vBoxInColumnName.getChildren().get(i+1);
            s1 = map.get("InColName"+i);
            ((TextField) node).setText(s1);
            node = vBoxOutColumnName.getChildren().get(i+1);
            s2 = map.get("OuColName"+i);
            ((TextField) node).setText(s2);
            if(s1==null&&s2==null){
                break;
            }
            onActionAddNewField();
        }
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
