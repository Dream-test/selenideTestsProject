package aetestproject.utils;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;

public class CustomActions {

    public static void waitForVisibilityAndClick(SelenideElement element) {
        element.should(exist);
        element.shouldBe(visible).click();
    }

    public static void scrollToElement(SelenideElement element) {
        element.scrollIntoView(false).shouldBe(visible);
    }
}
