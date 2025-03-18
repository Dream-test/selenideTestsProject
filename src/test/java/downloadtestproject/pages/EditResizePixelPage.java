package downloadtestproject.pages;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.Condition;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class EditResizePixelPage {
    private final SelenideElement pageHeader = $("h1.text-secondary");

    public void isResizePageLoaded() {
        pageHeader.shouldBe(visible)
                .shouldHave(Condition.text("Изменить и просмотреть изображение"));

    }

    public void clickDownloadButton() {
        SelenideElement goToDownloadButton = $("a#downloadBtn");
        goToDownloadButton.shouldBe(visible)
                .shouldBe(clickable)
                .shouldHave(Condition.attribute("role", "button"))
                .shouldHave(Condition.text("Перейти к Скачиванию"))
                .click();

        pageHeader.shouldNotHave(Condition.text("Изменить и просмотреть изображение"));
    }
}
