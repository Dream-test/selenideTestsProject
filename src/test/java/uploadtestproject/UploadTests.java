package uploadtestproject;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.url;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static uploadtestproject.utils.DragAndDropUtils.dragAndDropFile;
import static uploadtestproject.utils.DragAndDropUtils.dragAndDropFileByBaseObject;

public class UploadTests {
    private static final Logger logger = LoggerFactory.getLogger(UploadTests.class);

    @BeforeAll
    public static void configurationBrowser() {
        Configuration.browserSize = "1366x768";
        Configuration.headless = false;
        Configuration.timeout = 10000;
    }
/*
    @BeforeEach
    public void openUploadPage() {
        String uploadPageUrl = "https://bonigarcia.dev/selenium-webdriver-java/web-form.html";
        open(uploadPageUrl);
    }

 */

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
    void uploadFileTest() throws IOException {
        String filePath = "src/test/java/resources/text.txt";

        //Read file contents as a String
        String content = new String(Files.readAllBytes(Paths.get(filePath)));

        //Use file contents in the workflow code
        logger.info("File content: {}", content);
        System.out.println("File content: " + content);

        //Get URL for file resource
        URL url = UploadTests.class.getClassLoader().getResource("text.txt");

        String absolutePath;
        if (url != null) {
            //Get absolute path for the file
            absolutePath = new File(url.getPath()).getAbsolutePath();
            logger.info("Absolute path: {}", absolutePath);
            System.out.println("Absolute path: " + absolutePath);
        } else {
            logger.info("Resource is not find");
        }

        //Open the page with upload file form (in @BeforeEach)
        String uploadPageUrl = "https://bonigarcia.dev/selenium-webdriver-java/web-form.html";
        open(uploadPageUrl);

        //Find element <input type="file" for its attribute and name
        SelenideElement fileInput = $("input[name='my-file']");

        //Upload file by absolute path
        fileInput.uploadFile(new File(filePath));
        sleep(2000);

        //Click "Submit button"
        $(Selectors.byTagAndText("button", "Submit")).click();

        sleep(2000);
        assertThat($("p.lead").getText()).contains("Received!");
        assertThat(url()).contains("text.txt");
    }

    @Test
    void uploadFileRealSiteTest() {
        String pageUrl = "https://photoaid.com/ru/ru/tools/greyscale";
        open(pageUrl);
        $("h1.Header_title__2ad0I").shouldBe(visible)
                .shouldHave(text("Бесплатный фото редактор для черно-белого фото"));

        SelenideElement allowsCookieButton = $(By.id("CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll"));
        if (allowsCookieButton.exists()) {
            allowsCookieButton.click();
            allowsCookieButton.should(disappear);
        }

        SelenideElement advertiseDialog = $("div#card");
        if (advertiseDialog.exists()) {
            advertiseDialog.$("div#dismiss-button").click();
            advertiseDialog.should(disappear);
        }

        String filePath = "src/test/java/resources/sample.png";

        SelenideElement fileInput = $("input[type='file']");

        fileInput.uploadFile(new File(filePath));

        SelenideElement downloadButton = $("a[download]");
        downloadButton.shouldBe(visible);
        assertThat(downloadButton.getText()).contains("Download");
    }

    @Test
    void simpleDragAndDropTest() {
        String pageUrl = "https://photoaid.com/ru/ru/tools/greyscale";
        open(pageUrl);

        SelenideElement allowsCookieButton = $(By.id("CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll"));
        if (allowsCookieButton.exists()) {
            allowsCookieButton.click();
            allowsCookieButton.should(disappear);
        }

        File file = new File("src/test/java/resources/sample.png");

        SelenideElement dragTarget = $x("//section");

        dragAndDropFile(file, dragTarget);
    }

    @Test
    void secondDragAndDropTest() throws IOException {
        // Открываем страницу с зоной загрузки
        String pageUrl = "https://photoaid.com/ru/ru/tools/greyscale";
        open(pageUrl);

        // Определяю, dragTarget как SelenideElement
        SelenideElement dragTarget = $x("//section");

        // Файл для загрузки
        File file = new File("src/test/java/resources/sample.png");

        // Выполняем drag-and-drop загрузку файла
        dragAndDropFileByBaseObject(file, dragTarget);

        // Далее можно добавить проверки, что файл успешно загружен (например, через появление определённого элемента)
        SelenideElement downloadButton = $("a[download]");
        downloadButton.shouldBe(visible);
        assertThat(downloadButton.getText()).contains("Download");
    }

    @Test
    void apiUploadTest() {
        //Only for working with @Before and @After - not need for test workflow
        String pageUrl = "https://petstore.swagger.io/";
        open(pageUrl);

        //URL API for uploading image
        String apiUrl = "https://petstore.swagger.io/v2/pet/1/uploadImage";
        //Create object File which show the image for upload by file path
        File file = new File("src/test/java/resources/sample.png");
         //Create and send POST-request to API with image
        Response response =
                given()
                        .header("accept", "application/json")
                        .contentType("multipart/form-data")
                        .multiPart("file", file, "image/png")
                .when()
                        .post(apiUrl)
                .then()
                        .statusCode(200)
                        .extract()
                        .response();
        logger.info("Response: {}", response.asString());
        System.out.println("Response: " + response.asString());
    }
}
