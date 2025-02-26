package org.webproject;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.clickable;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class JQueryUIPage {
    private final SelenideElement pageTitle = $(Selectors.byTagAndText("h3", "JQuery UI"));
    private final SelenideElement menuButton = $(Selectors.byTagAndText("a", "Menu"));

    public void JQueryUIPageLoaded() {
        pageTitle.shouldHave(Condition.text("JQuery UI")).shouldBe(visible);
        menuButton.shouldHave(Condition.text("Menu"))
                .shouldHave(Condition.attribute("href", "https://the-internet.herokuapp.com/jqueryui/menu"))
                .shouldBe(visible)
                .shouldBe(clickable);
    }

    public SelenideElement getMenuButton() {
        return menuButton;
    }
}
