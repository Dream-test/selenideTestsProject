package uploadtestproject;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.FileDownloadMode;
import com.codeborne.selenide.SelenideElement;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static uploadtestproject.utils.DocUtils.searchText;
import static uploadtestproject.utils.ExcelUtils.printText;
import static uploadtestproject.utils.ExcelUtils.searchSheetText;
import static uploadtestproject.utils.ImageComparisonUtils.*;
import static uploadtestproject.utils.PdfUtils.readPdf;
import static uploadtestproject.utils.PdfUtils.savePdf;

public class FileContentTests {
    Logger logger = LoggerFactory.getLogger(FileContentTests.class);

    @Test
    void downloadAndCheckImageTest() {
        Configuration.browserSize = "1366x768";
        Configuration.headless = false;
        Configuration.timeout = 10000;
        Configuration.fileDownload = FileDownloadMode.FOLDER;
        Configuration.downloadsFolder = "target/downloads";

        String filePath = "src/test/java/resources/sample.png";
        File expectingImage = new File("src/test/java/resources/sample-photoaidcom-greyscale.png");

        String pageUrl = "https://photoaid.com/ru/ru/tools/greyscale";
        open(pageUrl);
        $("h1.Header_title__2ad0I").shouldBe(visible)
                .shouldHave(text("Бесплатный фото редактор для черно-белого фото"));

        SelenideElement allowsCookieButton = $(By.id("CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll"));
        if (allowsCookieButton.exists()) {
            allowsCookieButton.click();
            allowsCookieButton.should(disappear);
        }

        SelenideElement fileInput = $("input[type='file']");
        fileInput.uploadFile(new File(filePath));

        SelenideElement downloadButton = $("a[download]");
        downloadButton.shouldBe(visible);
        assertThat(downloadButton.getText()).contains("Download");

        File downloadedFile = downloadButton.download();
        Assertions.assertThat(downloadedFile).exists();

        //Получить файл с указанием абсолютного пути
        File actualImage = downloadedFile.getAbsoluteFile();

        boolean isImagesEquals = compareImages(expectingImage, actualImage);
        assertThat(isImagesEquals).isTrue();
    }

    @Test
    void downloadHttpClientTest() {
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
        String pdfText = readPdf(fileName);

        logger.info("Pdf document text: {}", pdfText);

        assertThat(pdfText).contains("кому")
                .contains("Прошу перечислять мою заработную плату");
    }

    @Test
    void checkDocTest() {
        String filePath = "src/test/java/resources/Lorem Ipsum.docx";

        assertThat(searchText(filePath, "Selenide")).isFalse();
        assertThat(searchText(filePath, "Lorem ipsum dolor")).isTrue();

    }

    @Test
    void checkExcelTest() {
        String filePath = "src/test/java/resources/Sheet.xlsx";

        printText(filePath);
        assertThat(searchSheetText(filePath, "Benetton")).isFalse();
        assertThat(searchSheetText(filePath, "Audi")).isTrue();
    }
}
