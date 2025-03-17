package uploadtestproject.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ExcelUtils {
    public static final Logger logger = LoggerFactory.getLogger(ExcelUtils.class);

    public static void printText(String filePath) {
        try (InputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            //Получить первый лист
            Sheet sheet = workbook.getSheetAt(0);

            //Итерация по строкам и ячейкам листа
            for (Row row : sheet) {
                for (Cell cell : row) {
                    //Получить значение ячейки в зависимости от типа данных
                    switch (cell.getCellType()) {
                        case STRING -> System.out.print(cell.getStringCellValue() + "\t");
                        case NUMERIC -> {
                            if (DateUtil.isCellDateFormatted(cell)) {
                                System.out.print(cell.getDateCellValue() + "\t");
                            } else {
                                System.out.print(cell.getNumericCellValue() + "\t");
                            }
                        }
                        case BOOLEAN -> System.out.print(cell.getBooleanCellValue() + "\t");
                        case FORMULA -> System.out.print(cell.getCellFormula() + "\t");
                        default -> System.out.print("UNKNOWN\t");
                    }
                }
                System.out.println();
            }
        } catch (IOException e) {
            logger.error("printText IOException", e);
        }
    }

    public static boolean searchSheetText(String filePath, String textToSearch) {
        try (InputStream fis = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fis)) {
             //Получить первый лист
            Sheet sheet = workbook.getSheetAt(0);

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
        } catch (IOException e) {
            logger.error("searchText IOException", e);
        }
        return false;
    }
}
