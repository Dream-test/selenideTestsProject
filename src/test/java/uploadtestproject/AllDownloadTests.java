package uploadtestproject;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.FileDownloadMode;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.assertj.core.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static uploadtestproject.utils.PdfUtils.savePdf;

public class AllDownloadTests {
    private final Logger logger = LoggerFactory.getLogger(AllDownloadTests.class);

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

        if (downloadFolder.exists() && downloadFolder.isDirectory()) {
            File[] files = downloadFolder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        if (file.delete()) {
                            logger.info("Deleted file: {}", file.getName());
                        } else {
                            logger.info("File deletion error: {}", file.getName());
                        }
                    }
                }
            }
        }
    }

    @AfterAll
    public static void tearDownAll() {
        closeWebDriver();
    }

    @Test
    void DownloadButtonGetTest() {
        Configuration.fileDownload = FileDownloadMode.HTTPGET;
        Configuration.downloadsFolder = "target/downloads";

        String downloadPageUrl = "https://bonigarcia.dev/selenium-webdriver-java/download.html";
        open(downloadPageUrl);

        SelenideElement downloadButton = $("a[download='webdrivermanager.png']");
        File downloadedFile = downloadButton.download();

        Assertions.assertThat(downloadedFile).exists();
        Assertions.assertThat(downloadedFile).hasFileName("webdrivermanager.png");
    }

    @Test
    void downloadButtonFolderTest() {
        Configuration.fileDownload = FileDownloadMode.FOLDER;
        Configuration.downloadsFolder = "target/downloads";

        String downloadPageUrl = "https://bonigarcia.dev/selenium-webdriver-java/download.html";
        open(downloadPageUrl);

        SelenideElement downloadButton = $("a[download='webdrivermanager.pdf']");
        File downloadedFile = downloadButton.download();

        Assertions.assertThat(downloadedFile).exists();
        Assertions.assertThat(downloadedFile).hasFileName("webdrivermanager.pdf");
    }

    @Test
    void downloadCdpTest() {
        Configuration.fileDownload = FileDownloadMode.CDP;
        Configuration.downloadsFolder = "target/downloads";

        String downloadPageUrl = "https://bonigarcia.dev/selenium-webdriver-java/download.html";
        open(downloadPageUrl);

        SelenideElement downloadButton = $("a[download='selenium-jupiter.png']");
        File downloadedFile = downloadButton.download();

        Assertions.assertThat(downloadedFile).exists();
        Assertions.assertThat(downloadedFile).hasFileName("selenium-jupiter.png");
    }

    @Test
    void downloadHttpClientTest() {
        //Only for working with @Before and @After - not need for test workflow
        String pageUrl = "https://bonigarcia.dev/selenium-webdriver-java/download.html";
        open(pageUrl);

        String endpoint = "https://alfabank.servicecdn.ru/site-upload/67/dd/356/zayavlenie-IZK.pdf";
        String fileName = "downloaded.pdf";

        Response response = given()
                .when()
                .get(endpoint)
                .then()
                .contentType("application/pdf")
                .statusCode(200)
                .extract().response();

        savePdf(response, fileName);

        //Проверка, что файл скачан успешно
        File downloadedFile = new File(fileName);
        Assertions.assertThat(downloadedFile).exists();
    }
}
