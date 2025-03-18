package downloadtestproject.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.clickable;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MsTemplatePage {

    public void isMsTemplatePageLoaded() {
        SelenideElement startCreation = $$("span[data-testid='typography-caption']").first();
        startCreation.shouldBe(visible).shouldHave(Condition.text("Начните создание с:"));
    }

    public void selectWordTemplate() {
        SelenideElement wordTemplateButton = $("button[aria-label='WORD-button']");
        wordTemplateButton.shouldBe(visible).click();
        $("a[title='Обзор шаблонов резюме']").shouldBe(visible)
                .shouldBe(clickable)
                .click();
    }
}
