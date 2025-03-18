package downloadtestproject;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.FileDownloadMode;
import com.codeborne.selenide.Selenide;
import downloadtestproject.modaldialogs.RPCookiesDialog;
import downloadtestproject.pages.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.url;
import static downloadtestproject.utils.CheckExcelFile.checkSheetText;
import static downloadtestproject.utils.CheckWordFile.searchWordText;
import static downloadtestproject.utils.CleanDirectory.cleanTargetDirectory;
import static downloadtestproject.utils.ImageComparison.compareImages;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DownloadAndContentTests {
    private static final Logger logger = LoggerFactory.getLogger(DownloadAndContentTests.class);

    @BeforeEach
    public void configurationBrowser() {
        Configuration.browserSize = "1366x768";
        Configuration.headless = false;
        Configuration.timeout = 10000;
    }

    @AfterEach
    public void cleanBrowser() {
        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();

        File downloadFolder = new File("target/downloads");
        cleanTargetDirectory(downloadFolder);
    }

    @AfterAll
    public static void tearDownAll() {
        closeWebDriver();

    }

    @Test
    public void forImageTest() {
        Configuration.fileDownload = FileDownloadMode.FOLDER;
        Configuration.downloadsFolder = "target/downloads";
        String imageFilePath = "src/test/java/resources/sample.png";
        File expectingImage = new File(imageFilePath);
        String currentUrl = "https://www.resizepixel.com/";
        open(currentUrl);
        logger.info("Opened URL: {}", url());

        UploadResizePixelPage uploadResizePixelPage = new UploadResizePixelPage();
        uploadResizePixelPage.isUploadPageLoaded();
        sleep(3000);

        RPCookiesDialog rpCookiesDialog = new RPCookiesDialog();
        if (rpCookiesDialog.isDialogExist()) {
            rpCookiesDialog.acceptAllCookies();
        }

        uploadResizePixelPage.getImageInput().uploadFile(expectingImage);

        EditResizePixelPage editResizePixelPage = new EditResizePixelPage();
        editResizePixelPage.isResizePageLoaded();
        editResizePixelPage.clickDownloadButton();

        DownloadResizePixelPage downloadResizePixelPage = new DownloadResizePixelPage();
        downloadResizePixelPage.isDownloadPageLoaded();
        File downloadedImageFile = downloadResizePixelPage.getDownloadButton().download();
        assertThat(downloadedImageFile).exists();

        File actualImage = downloadedImageFile.getAbsoluteFile();

        boolean isImagesEqual = compareImages(expectingImage, actualImage);
        assertThat(isImagesEqual).isTrue();
    }

    @Test
    public void forExcelTest() {
        String[] textToSearch = {"Коммерческий департамент", "Внешний идентификатор для импорта", "Вышестоящий отдел"};
        Configuration.fileDownload = FileDownloadMode.FOLDER;
        Configuration.downloadsFolder = "target/downloads";

        String currentUrl = "https://itsm365.com/documents_rus/web/Content/import/import_org_file.htm";
        open(currentUrl);
        logger.info("Opened URL: {}", url());

        XlsTemplatesPage xlsTemplatesPage = new XlsTemplatesPage();
        xlsTemplatesPage.isXlsTemplatesPageLoaded();

        File downloadedXlsFile = xlsTemplatesPage.getDownloadXlsButton().download();
        assertThat(downloadedXlsFile).exists();

        String filePath = downloadedXlsFile.getAbsolutePath();
        logger.info("File Path: {}", filePath);

        for (String text : textToSearch) {
            boolean searchResul = checkSheetText(filePath, text);
            assertThat(searchResul).isTrue();
        }
    }

    @Test
    public void forWordTest() {
        String textToFind = "Специалист отдела кадров";
        Configuration.fileDownload = FileDownloadMode.FOLDER;
        Configuration.downloadsFolder = "target/downloads";

        String currentUrl = "https://create.microsoft.com/ru-ru";
        open(currentUrl);
        logger.info("Opened URL: {}", url());

        MsTemplatePage msTemplatePage = new MsTemplatePage();
        msTemplatePage.isMsTemplatePageLoaded();
        msTemplatePage.selectWordTemplate();

        WordTemplatePage wordTemplatePage =new WordTemplatePage();
        wordTemplatePage.isWordTemplatePageLoaded();
        wordTemplatePage.selectSecondTemplate();

        DownloadMsTemplatePage downloadMsTemplatePage = new DownloadMsTemplatePage();
        downloadMsTemplatePage.isDownloadPageLoaded();
        File downloadedWordFile = downloadMsTemplatePage
                .getTemplateDownloadButton()
                .download();

        String filePath = downloadedWordFile.getAbsolutePath();
        logger.info("File path: {}", filePath);

        boolean isTextFind = searchWordText(filePath, textToFind);
        assertThat(isTextFind).isTrue();
   }


}
