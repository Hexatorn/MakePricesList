package hexatorn.util;

import javafx.scene.control.Alert;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by Hexatorn on 2017-06-17.
 */
public class ReadXLSXFile {
    public static void test(){
        System.out.println("odczyt pliku");
        File file = new File("C:\\Duka Make Product List\\Test.xls");
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
            HSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            HSSFRow row;
            int tmpcounter = 1;
            Cell cell;
            while (rowIterator.hasNext()){

                row = ((HSSFRow) rowIterator.next());
//                System.out.println(row.getCell(1));

                Iterator<Cell> cellIterator = row.cellIterator();



                String rowString ="";
                //odczyt SKU
                cell = row.getCell(0);
                rowString = cellsReader(cell, rowString);
                if (rowString.equals("0")&&tmpcounter>=10)
                    break;
                rowString = rowString + "\\";
                //odczyt EAN
                cell = row.getCell(1);
                rowString = cellsReader(cell, rowString);
                rowString = rowString + "\\";
                //odczyt nazw
                cell = row.getCell(2);
                if (cell != null) {
                    System.out.println("Wiersz: " + (tmpcounter++) + " " + cell.getCellTypeEnum());
                }else{
                    System.out.println("Wiersz: " + (tmpcounter++) + " NULL");
                }
                rowString = cellsReader(cell, rowString);
                rowString = rowString + "\\";
                //wypisz
                System.out.println(rowString);

//                if (tmpcounter >= 10)
//                    break;
            }

        } catch (FileNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Alert FileNotFoundException");
            alert.setHeaderText("Błąd odczytu pliku danych. Nie odnaleziono pliku z danymi żródłowymi");
            alert.setContentText(""+e.getMessage());
            alert.showAndWait();

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Alert IOException");
            alert.setHeaderText("Błąd odczytu pliku danych.");
            alert.setContentText(""+e.getMessage());
            alert.showAndWait();
        }
        catch (RuntimeException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Alert RuntimeException");
            alert.setHeaderText("Błąd odczytu pliku danych.");
            alert.setContentText(""+e.getMessage());
            alert.showAndWait();
        }
    }

    private static String cellsReader(Cell cell, String rowString) {
        if (cell == null){
            rowString = rowString + "-";
        }else if (cell.getCellTypeEnum() == CellType.FORMULA) {
            try {//odczytanie danych liczbowych z formuły
                double d = cell.getNumericCellValue();
                String tmp = String.format("%13.0f", d);
                rowString = rowString + tmp;
            }catch (RuntimeException e) {
                try {//odczytanie danych tekstowych z formuły
                    rowString = rowString + cell.getStringCellValue();
                }catch (RuntimeException e1){
                    rowString = rowString +"-";
                }
            }
        }
        else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
            rowString = rowString + (int) cell.getNumericCellValue();
        }
        else if (cell.getCellTypeEnum() == CellType.STRING) {
            rowString = rowString + cell.getStringCellValue();
        }
        else if (cell.getCellTypeEnum() == CellType.BLANK){
            rowString = rowString + "-";
        }
        return rowString;
    }
}
