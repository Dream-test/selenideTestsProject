package downloadtestproject.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.clickable;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class DownloadMsTemplatePage {

    public void isDownloadPageLoaded() {
        $("button[aria-label='Скачать']")
                .shouldBe(visible)
                .shouldBe(clickable);
    }

    public SelenideElement getTemplateDownloadButton() {
        return $("button[aria-label='Скачать']");
    }
}
