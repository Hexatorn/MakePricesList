package hexatorn.util;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static hexatorn.controlers.AlertWindowsControler.showFileNotFoundExceptionMessageSaveFile;
import static hexatorn.controlers.AlertWindowsControler.showIOExceptionMessage;

/**
 * Created by Hexatorn on 2017-06-19.
 */
public class WriteXLSX {
    public static void writeExcel(){
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        XSSFRow row;
        for (int i = 0; i < 10; i++) {
            row = sheet.createRow(i);
            row.createCell(0).setCellValue("Test zapisu nr "+i);
            row.createCell(1).setCellValue(i*100);
        }
        File file = new File("C:\\Duka Make Product List\\Cennik NL.xlsx");
        try {
            FileOutputStream out = new FileOutputStream(file);
            workbook.write(out);
            out.close();
        } catch (FileNotFoundException e) {
            showFileNotFoundExceptionMessageSaveFile(e);
        } catch (IOException e) {
            showIOExceptionMessage(e);
        }
    }
}
