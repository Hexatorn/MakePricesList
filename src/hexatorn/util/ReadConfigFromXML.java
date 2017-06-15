package hexatorn.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;


/**
 * Created by Hexatorn on 2017-06-12.
 */
public class ReadConfigFromXML {

    private static Document doc = null;

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
