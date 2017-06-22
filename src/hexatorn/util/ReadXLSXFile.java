package hexatorn.util;

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
import java.util.*;

import static hexatorn.controlers.AlertWindowsControler.*;

/**
 * Created by Hexatorn on 2017-06-17.
 */
public class ReadXLSXFile {
    private static String nullStringValue = "null";//zaślepka dla pusteych komórek "Cell == null" ,#ARG ,#ND
    private static HSSFWorkbook workbook = null;

    //private static int headersLine = 3;

    public static void readData(ArrayList<String> arrayList, int headersLine){
        readFile();
        Map<String,Integer> stringIntegerMap;
        stringIntegerMap = readHeaders(arrayList,headersLine);
        ArrayList<Integer> indexToRead = new ArrayList<>(stringIntegerMap.values());
        Collections.sort(indexToRead);
        ArrayList<String> simplyProductCard;
        ArrayList<ArrayList<String>> simplyProductCards = new ArrayList<>();

        try {
            HSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            HSSFRow row;
            int nullCounter = 1;
            Cell cell;
            for (int i = 0; i < headersLine; i++) {
                rowIterator.next();
            }
            stopRead:
            while (rowIterator.hasNext()){
                row = ((HSSFRow) rowIterator.next());
                simplyProductCard = new ArrayList<>();
                if(cellsReader(row.getCell(0)).equals(nullStringValue)) {
                    nullCounter++;
                    if (nullCounter >= 5)
                        break stopRead;
                }
                else if (nullCounter != 0)
                    nullCounter = 0;
                for (Integer index : indexToRead) {
                    cell = row.getCell(index);
                    simplyProductCard.add(cellsReader(cell));
                }
                simplyProductCards.add(simplyProductCard);
            }
            System.out.println(simplyProductCards);
        } catch (RuntimeException e){
            showRuntimeExceptionMessage(e);
        }
    }

    private static Map<String, Integer> readHeaders(ArrayList<String> arrayList, int headersLine){
        if (workbook==null)
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
                if (cellString.equals(nullStringValue))
                    blankLine++;
                else {
                    blankLine = 0;
                }
            }
            next ++;
        }
        return stringIntegerMap;
    }

    private static void readFile(){
        File file = new File("C:\\Duka Make Product List\\Test.xls");
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(file);
            workbook = new HSSFWorkbook(fileInputStream);
        } catch (FileNotFoundException e) {
            showFileNotFoundExceptionMessage(e);
        } catch (IOException e) {
            showIOExceptionMessage(e);
        }
    }


    private static String cellsReader (Cell cell){
        return cellsReader(cell,"");
    }
    private static String cellsReader(Cell cell, String rowString) {

        if (cell == null){
            rowString = rowString + nullStringValue;
        }else if (cell.getCellTypeEnum() == CellType.FORMULA) {
            try {//odczytanie danych liczbowych z formuły
                rowString = rowString + doubleConverter(cell);
            }catch (RuntimeException e) {
                try {//odczytanie danych tekstowych z formuły
                    rowString = rowString + cell.getStringCellValue();
                }catch (RuntimeException e1){
                    rowString = rowString + nullStringValue;
                }
            }
        }
        else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
            rowString = rowString + doubleConverter(cell);
        }
        else if (cell.getCellTypeEnum() == CellType.STRING) {
            rowString = rowString + cell.getStringCellValue();
        }
        else if (cell.getCellTypeEnum() == CellType.BLANK){
            rowString = rowString + nullStringValue;
        }
        return rowString;
    }
    private static String doubleConverter(Cell cell){
        double d = cell.getNumericCellValue();
        String formatedString;
        if(d>=99999){
            formatedString = String.format("%.0f", d);
        }else{
            formatedString = String.format("%.2f", d);
        }
        return formatedString;
    }


}
