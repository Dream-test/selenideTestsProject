package org.webproject;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.clickable;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class MenuElements {
    private final SelenideElement disabledMenuButton = $(By.id("ui-id-1"));
    private final SelenideElement enabledMenuButton = $(By.id("ui-id-3"));
    private final SelenideElement downloadsButton = $(By.id("ui-id-4"));
    private final SelenideElement backButton = $(By.id("ui-id-8"));
    private final SelenideElement pdfButton = $(By.id("ui-id-5"));
    private final SelenideElement csvButton = $(By.id("ui-id-6"));
    private final SelenideElement excelButton = $(By.id("ui-id-7"));

    public void getSecondLevel() {
        enabledMenuButton.hover();
        backButton.$("a")
                .shouldHave(Condition.text("Back to JQuery UI"))
                .shouldHave(Condition.attribute("href", "https://the-internet.herokuapp.com/jqueryui"))
                .shouldBe(visible)
                .shouldBe(clickable);
        downloadsButton.$("a").shouldHave(Condition.text("Downloads")).shouldBe(visible);
    }

    public void transitionToBack() {
        getSecondLevel();
        backButton.click();
    }

    public void getThirdLevel() {
        getSecondLevel();
        downloadsButton.hover();
        pdfButton.$("a")
                .shouldHave(Condition.text("PDF"))
                .shouldHave(Condition.attribute("href", "https://the-internet.herokuapp.com/download/jqueryui/menu/menu.pdf"))
                .shouldBe(visible)
                .shouldBe(clickable);
        csvButton.$("a")
                .shouldHave(Condition.text("CSV"))
                .shouldHave(Condition.attribute("href", "https://the-internet.herokuapp.com/download/jqueryui/menu/menu.csv"))
                .shouldBe(visible)
                .shouldBe(clickable);
        excelButton.$("a")
                .shouldHave(Condition.text("Excel"))
                .shouldHave(Condition.attribute("href", "https://the-internet.herokuapp.com/download/jqueryui/menu/menu.xls"))
                .shouldBe(visible)
                .shouldBe(clickable);
    }

    public SelenideElement getPdfButton() {
        return pdfButton;
    }

    public SelenideElement getCsvButton() {
        return csvButton;
    }

    public SelenideElement getExcelButton() {
        return excelButton;
    }
}
