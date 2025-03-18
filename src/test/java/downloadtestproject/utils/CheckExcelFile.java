package downloadtestproject.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CheckExcelFile {
    private static final Logger logger = LoggerFactory.getLogger(CheckExcelFile.class);

    public static boolean checkSheetText(String filePath, String textToSearch) {
        try (InputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            int countOfSheet = workbook.getNumberOfSheets();

            for (int i= 0; i < countOfSheet; i++) {
                //Получить первый лист
                Sheet sheet = workbook.getSheetAt(i);

                //Итерация по строкам и ячейкам листа
                for (Row row : sheet) {
                    for (Cell cell : row) {
                        //Если ячейка имеет формат строки - получаем значение
                        if (cell.getCellType().equals(CellType.STRING)) {
                            String text = cell.getStringCellValue();
                            if (text.equals(textToSearch)) {
                                return true;
                            }
                        }
                    }
                    System.out.println();
                }
            }
            } catch(IOException e){
                logger.error("searchText IOException", e);
            }
            return false;
        }
}
