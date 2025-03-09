package aetestproject.castoms;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class CustomActions {

    public static void waitForVisibilityAndClick(SelenideElement element) {
        element.should(exist);
        element.shouldBe(visible).click();
    }

    public static void handleCookieConsent() {
        SelenideElement cookieConsentButton = $(By.id("onetrust-accept-btn-handler"));
        if (cookieConsentButton.exists()) {
            waitForVisibilityAndClick(cookieConsentButton);
            cookieConsentButton.should(disappear);
        }
    }

    public static void scrollToElement(SelenideElement element) {
        element.scrollIntoView(false).shouldBe(visible);
    }
}
