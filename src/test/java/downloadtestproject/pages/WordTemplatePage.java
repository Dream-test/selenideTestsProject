package downloadtestproject.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class WordTemplatePage {

    public void isWordTemplatePageLoaded() {
        SelenideElement templatePageHeader = $("h1[data-testid='typography-h1']");
        templatePageHeader.shouldBe(visible)
                .shouldHave(Condition.text("Шаблоны резюме"));
    }

    public void selectSecondTemplate() {
        $$("div[role='listitem']").get(1).click();
    }
}
