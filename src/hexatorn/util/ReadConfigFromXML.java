package hexatorn.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;


/**
 * Created by Hexatorn on 2017-06-12.
 */
public class ReadConfigFromXML {

    private static Document doc = null;


    public static Map<String, String> getConfigFieldsValue (String profileName){
        Map<String, String> map = new TreeMap<>();
        readXMLFile();

        NodeList nodeList = doc.getElementsByTagName("Profile");
        for (int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            Element element = ((Element) node);
            if (element.getElementsByTagName("ProfileName").item(0).getTextContent().equals(profileName)){
                String value;
                value = element.getElementsByTagName("ProfileName").item(0).getTextContent();
                map.put("ProfileName",value);
                value = element.getElementsByTagName("DefaultInputFilePath").item(0).getTextContent();
                map.put("DefaultInputFilePath",value);
                value = element.getElementsByTagName("InputNumberOfHeaderLines").item(0).getTextContent();
                map.put("InputNumberOfHeaderLines",value);
                value = element.getElementsByTagName("OutputDefaultFIlePath").item(0).getTextContent();
                map.put("OutputDefaultFIlePath",value);
                /*
                 * Wczytanie nazw kolumn dla pliku źródłowego
                 */
                for (int j = 0;; j++) {
                    try {
                        value = element.getElementsByTagName("InColName").item(j).getTextContent();
                        map.put("InColName"+j,value);
                        value = element.getElementsByTagName("OuColName").item(j).getTextContent();
                        map.put("OuColName"+j,value);
                    }
                    catch (NullPointerException e){
                        break; //koniec pętli forj
                    }
                }

                break; //koniec pętli fori
            }
        }
        return map;
    }

    public static ArrayList<String> getProfileName(){
        ArrayList<String> arrayList = new ArrayList<>();
        if (doc==null)
            readXMLFile();
        NodeList nodeList = doc.getElementsByTagName("Profile");

        for (int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            Element element = ((Element) node);
            arrayList.add(element.getElementsByTagName("ProfileName").item(0).getTextContent());
        }
        return arrayList;
    }

    private static void readXMLFile(){
        File file = new File(System.getProperty("user.home")+"\\AppData\\Roaming\\Duka Make Price List\\Config.xml");


        try{
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
        }
        catch(FileNotFoundException e){
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning Alert");
            alert.setHeaderText("Błąd odczytu pliku konfiguracyjknego.\nPlik nie istnieje lub nie można go odczytać.\nBrak dostępu do zapamiętanych ustawień.");
            alert.setContentText(""+e.getMessage()+
                    "\n\n Możliwe sposoby rozwiązania problemu:\n"+
                    "1. Sprawdzenie uprawnień do katalogu i\\lub pliku.\n"+
                    "2. Przekopiowanie pliku konfiguracyjnego do z innego komputera.\n"+
                    "3. Stworzenie nowej konfiguracji w programie i zapisanie.");
            alert.showAndWait();
        } catch (ParserConfigurationException | IOException e) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning Alert");
            alert.setHeaderText("Błąd odczytu pliku konfiguracyjknego.");
            alert.setContentText(""+e.getMessage());
            alert.showAndWait();
            e.printStackTrace();
        } catch (SAXException e) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning Alert");
            alert.setHeaderText("Błąd odczytu pliku konfiguracyjknego.\n\nBrak dostępu do zapamiętanych ustawień.");
            alert.setContentText(""+e.getMessage()+
                    "\n\n Możliwe sposoby rozwiązania problemu:\n"+
                    "1. Przekopiowanie pliku konfiguracyjnego do z innego komputera.\n"+
                    "2. Stworzenie nowej konfiguracji i zapisanie w programie.");
            alert.showAndWait();
        }
    }

}
