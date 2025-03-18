package downloadtestproject.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class CleanDirectory {
    private static final Logger logger = LoggerFactory.getLogger(CleanDirectory.class);

    public static void cleanTargetDirectory(File directory) {
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                        deleteRecursively(file); // Удаляем файлы и папки внутри
                }
            }
        }
    }

    private static void deleteRecursively(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File subFile : files) {
                    deleteRecursively(subFile); // Удаляем вложенные файлы/папки
                }
            }
        }
        if (file.delete()) {
            logger.info("Deleted: {}", file.getAbsolutePath());
        } else {
            logger.warn("Failed to delete: {}", file.getAbsolutePath());
        }
    }
}
