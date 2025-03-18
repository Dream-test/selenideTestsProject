package downloadtestproject.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.clickable;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class DownloadResizePixelPage {
    private final SelenideElement pageHeader = $("h1.text-secondary");

    public void isDownloadPageLoaded() {
        pageHeader.shouldBe(visible)
                .shouldHave(Condition.text("Просмотреть и скачать изображение"));

    }

    public SelenideElement getDownloadButton() {
        SelenideElement downloadButton = $("a#downloadBtn");
        downloadButton.shouldBe(visible)
                .shouldBe(clickable)
                .shouldHave(Condition.attribute("role", "button"))
                .shouldHave(Condition.text("Скачать Изображение"));

        return downloadButton;
    }
}
