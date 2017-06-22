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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Hexatorn on 2017-06-17.
 */
public class ReadXLSXFile {

    private static HSSFWorkbook workbook = null;

    private static int headersLine = 3;

    public static void readHeaders(ArrayList<String> arrayList){
        System.out.println("odczyt nagłówka");
        readFile();
        HSSFSheet sheet = workbook.getSheetAt(0);
        HSSFRow row;
        Cell cell;
        String cellString;
        int blankLine = 0;
        int next = 0;
        Map<String, Integer> stringIntegerMap = new TreeMap<>();
        ArrayList<String> headersArrayList = ((ArrayList<String>) arrayList.clone());

        while (blankLine<10){
            cellString ="";
            findNextHeader:
            for (int i = 0; i < headersLine ; i++) {
                row = sheet.getRow(i);
                cell = row.getCell(next);
                cellString = cellsReader(cell);
                for (String header: headersArrayList){
                    if (header.equals(cellString)) {
                        int columnIndex = cell.getColumnIndex();
                        stringIntegerMap.put(header, columnIndex);
                        headersArrayList.remove(header);
                        break findNextHeader;
                    }
                }
                if (cellString.equals("-"))
                    blankLine++;
                else {
                    blankLine = 0;
                }
            }
            next ++;
        }
        System.out.println(stringIntegerMap);
    }



    public static void readData(){
        System.out.println("odczyt danych");
        readFile();
        try {
            HSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            HSSFRow row;
            int tmpcounter = 1;
            Cell cell;
            while (rowIterator.hasNext()){
                row = ((HSSFRow) rowIterator.next());
                String rowString ="";
                //odczyt SKU
                cell = row.getCell(0);
                rowString = cellsReader(cell, rowString);
                if (rowString.equals("-")&&tmpcounter>=10)
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
            }
        } catch (RuntimeException e){
            showRuntimeExceptionMessage(e);
        }
    }

    private static void readFile(){
        System.out.println("odczyt pliku");
        File file = new File("C:\\Duka Make Product List\\Test.xls");
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            workbook = new HSSFWorkbook(fileInputStream);
        } catch (FileNotFoundException e) {
            showFileNotFoundExceptionMessage(e);
        } catch (IOException e) {
            showIOExceptionMessage(e);
        }
    }

    private static void showRuntimeExceptionMessage (RuntimeException e){
        showMessage(
                "Warning Alert RuntimeException",
                "Błąd odczytu pliku danych.",
                ""+e.getMessage()
        );
    }

    private static void showIOExceptionMessage (IOException e){
        showMessage(
                "Warning Alert IOException",
                "Błąd odczytu pliku danych.",
                ""+e.getMessage()
        );
    }

    private static void showFileNotFoundExceptionMessage (FileNotFoundException e){
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
    private static String cellsReader (Cell cell){
        return cellsReader(cell,"");
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
