package uploadtestproject.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageComparisonUtils {
    private final static Logger logger = LoggerFactory.getLogger(ImageComparisonUtils.class);

    public static boolean compareImages(File file1, File file2) {
        BufferedImage img1 = null;
        BufferedImage img2 = null;

        try {
            img1 = ImageIO.read(file1);
        } catch (IOException e) {
            logger.info("Can not read file1: {}", e.getMessage());
        }
        try {
            img2 = ImageIO.read(file2);
        } catch (IOException e) {
            logger.info("Can not read file2: {}", e.getMessage());
        }

        assert img1 != null;
        assert img2 != null;

        if (img1.getWidth() != img2.getWidth() || img1.getHeight() != img2.getHeight()) {
            return false;
        }

        for (int y = 0; y < img1.getHeight(); y++) {
            for (int x = 0; x < img1.getWidth(); x++) {
                if (img1.getRGB(x, y) != img2.getRGB(x, y)) {
                  logger.info("img1 RGB: {}", img1.getRGB(x, y));
                  logger.info("img2 RGB: {}", img2.getRGB(x, y));
                  return false;
                }
            }
        }
        return true;
    }
}
