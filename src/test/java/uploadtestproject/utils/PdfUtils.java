package uploadtestproject.utils;

import io.restassured.response.Response;
import net.sourceforge.tess4j.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PdfUtils {
    private static final Logger logger = LoggerFactory.getLogger(PdfUtils.class);

    public static void savePdf(Response response, String fileName) {
        if (response != null) {
            //Извлекается содержимое из тела ответа в виде InputStream
            try (InputStream inputStream = response.getBody().asInputStream()) {

                // Определяем путь к директории "src/main/resources"
                String resourcesDir = "target/downloads";
                Path directoryPath = Paths.get(resourcesDir);

                // Создаем директорию, если её нет
                if (!Files.exists(directoryPath)) {
                    Files.createDirectories(directoryPath);
                }

                // Полный путь к файлу
                Path filePath = directoryPath.resolve(fileName);
                //Создание файла для сохранения PDF
                OutputStream outputStream = new FileOutputStream(filePath.toFile());

                //Копирование содержимого InputStream в файл
                int bytesRead;
                byte[] buffer = new byte[4096];
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                //Закрытие потоков
                inputStream.close();
                outputStream.close();

                logger.info("PDF saved in file: {}", filePath.toAbsolutePath());
            } catch (Exception e) {
                logger.error("Error saving PDF file: {}", fileName, e);
                e.printStackTrace();
            }
        }
    }

    public static String readPdf(String fileName) {
        String text = null;
        try {
            // Полный путь к файлу
            Path filePath = Paths.get("target/downloads", fileName);
            File file = filePath.toFile();

            //Загрузка PDF - документа
            PDDocument document = PDDocument.load(file);
/*
            //Для случая когда PDF содержит текстовые блоки (в виде текста)
            //Создание объекта PDFTextStripper
            PDFTextStripper pdfStripper = new PDFTextStripper();

            //Получение текста из PDF
            text = pdfStripper.getText(document);

            //Закрытие документа
            document.close();
        } catch (IOException e) {
            logger.error("Read PDF IOException trace", e);
        }
        return text;
 */
            //Для случая когда PDF - только изображение без текстовых блоков
            PDFRenderer pdfRenderer = new PDFRenderer(document);

            // Рендер первой страницы PDF с DPI = 300
            BufferedImage image = pdfRenderer.renderImageWithDPI(0, 300);

            ITesseract tesseract = new Tesseract();
            //tesseract.setDatapath("tessdata"); // путь к Tesseract OCR по умолчанию (язык en)

            //Переключение на обработку русского языка
            //Путь к папке с `ru.traineddata`
            tesseract.setDatapath("src/main/resources/tessdata");

            // Выбираю русский язык
            tesseract.setLanguage("rus");

            text = tesseract.doOCR(image);
            document.close();
            return text;
        } catch (Exception e) {
            logger.error("Read PDF Exception trace", e);
            return null;
        }
    }
}

