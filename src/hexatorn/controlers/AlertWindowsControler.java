package hexatorn.controlers;

import javafx.scene.control.Alert;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Hexatorn on 2017-06-22.
 */
public class AlertWindowsControler {
    public static void showRuntimeExceptionMessage (RuntimeException e){
        showMessage(
                "Warning Alert RuntimeException",
                "Błąd odczytu pliku danych.",
                ""+e.getMessage()
        );
    }

    public static void showIOExceptionMessage (IOException e){
        showMessage(
                "Warning Alert IOException",
                "Błąd odczytu pliku danych.",
                ""+e.getMessage()
        );
    }

    public static void showFileNotFoundExceptionMessage (FileNotFoundException e){
        showMessage(
                "Warning Alert FileNotFoundException",
                "Błąd odczytu pliku danych. Nie odnaleziono pliku z danymi żródłowymi",
                ""+e.getMessage()
        );
    }

    private static void showMessage (String title,String headers, String content){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(headers);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
