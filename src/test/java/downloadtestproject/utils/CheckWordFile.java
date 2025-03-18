package downloadtestproject.utils;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;

public class CheckWordFile {
    private final static Logger logger = LoggerFactory.getLogger(CheckWordFile.class);

    public static boolean searchWordText(String filePath, String textToFind) {
        try (FileInputStream fis = new FileInputStream(filePath);
             XWPFDocument document = new XWPFDocument(fis)) {
            boolean textFound = false;

            for (XWPFParagraph paragraph : document.getParagraphs()) {
                if (paragraph.getText().contains(textToFind)) {
                    textFound = true;
                    break;
                }
            }

            if (textFound) {
                logger.info("The document contains the specified text.");
                return true;
            } else {
                logger.info("The document does not contains the specified text.");
                return false;
            }

        } catch (IOException e) {
            logger.error("Search text error:", e);
            return false;
        }
    }
}
