package org.webproject;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.FileDownloadMode;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.*;

import java.io.File;

import static com.codeborne.selenide.Selenide.*;

public class DownloadTests {
    private final String url = "https://the-internet.herokuapp.com/jqueryui/menu";

    @BeforeAll
    public static void configurationBrowser() {
        Configuration.browserSize = "1366x768";
        Configuration.headless = true;
        Configuration.timeout = 10000;
        Configuration.proxyEnabled = true;
        Configuration.fileDownload = FileDownloadMode.PROXY;
    }

    @BeforeEach
    public void openMainPage() {
        open(url);
    }

    @AfterEach
    public void cleanBrowser() {
        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
    }

    @AfterAll
    public static void tearDownAll() {
        closeWebDriver();
    }

    @Test
    @DisplayName("DownloadPDF")
    public void expectDownloadPdf() {
        SelenideElement pdfButton = thirdLevelMenu().getPdfButton();
        executeJavaScript("arguments[0].click();", pdfButton);
        File downloadedFile = pdfButton.download();
        Assertions.assertNotNull(downloadedFile, "File didn't download");
        Assertions.assertEquals("menu.pdf", downloadedFile.getName(), "File name isn't equal");
    }

    @Test
    @DisplayName("DownloadCSV")
    public void expectDownloadCsv() {
        SelenideElement csvButton = thirdLevelMenu().getCsvButton();
        executeJavaScript("arguments[0].click();", csvButton);
        File downloadedFile = csvButton.download();
        Assertions.assertNotNull(downloadedFile, "File didn't download");
        Assertions.assertEquals("menu.csv", downloadedFile.getName(), "File name isn't equal");
    }

    @Test
    @DisplayName("DownloadExcel")
    public void expectDownloadExcel() {
        SelenideElement excelButton = thirdLevelMenu().getExcelButton();
        executeJavaScript("arguments[0].click();", excelButton);
        File downloadedFile = excelButton.download();
        Assertions.assertNotNull(downloadedFile, "File didn't download");
        Assertions.assertEquals("menu.xls", downloadedFile.getName(), "File name isn't equal");
    }

    private MenuElements thirdLevelMenu() {
        MenuPage menuPage = new MenuPage();
        menuPage.queryMenuPageLoaded();
        MenuElements menuElements = new MenuElements();
        menuElements.getSecondLevel();
        menuElements.getThirdLevel();
        return menuElements;
    }
}
