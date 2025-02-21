package org.webproject;

import com.codeborne.selenide.*;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class MenuPage {
    private SelenideElement pageTitle = $(Selectors.byTagAndText("h3", "JQueryUI - Menu"));
    private SelenideElement disabledMenuButton = $(By.id("ui-id-1"));
    private SelenideElement enabledMenuButton = $(By.id("ui-id-3"));
    private SelenideElement queryMenu = $(By.id("menu"));

    public void queryMenuPageLoaded() {
        pageTitle.shouldBe(visible).shouldHave(Condition.text("JQueryUI - Menu"));
    }

    public void ifDisabledButtonExist() {
        disabledMenuButton.$("a").shouldHave(Condition.text("Disabled"));

    }

    public void ifEnabledButtonExist() {
        enabledMenuButton.$("a").shouldHave(Condition.text("Enabled"));
    }


}

