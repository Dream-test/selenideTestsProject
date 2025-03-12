package aetestproject.modaldialogs;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static aetestproject.utils.CustomActions.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CookieAcceptDialog {
    public boolean isCookieAcceptDialogPresent() {
        SelenideElement modalBodyText = $(By.id("onetrust-policy-text"));
        return modalBodyText.isDisplayed();
    }

    public void isCookieAcceptDialogLoaded() {
        SelenideElement modalBodyText = $(By.id("onetrust-policy-text"));
        modalBodyText.shouldBe(visible);
        assertTrue(modalBodyText.getText().contains("We use cookies"));

    }

    public void acceptAllCookies() {
        SelenideElement acceptAllButton = $(By.id("onetrust-accept-btn-handler"));
        scrollToElement(acceptAllButton);
        waitForVisibilityAndClick(acceptAllButton);
        acceptAllButton.should(disappear);

    }

    public void doNotAcceptCookies() {
        SelenideElement doNotAcceptButton = $(By.id("onetrust-reject-all-handler"));
        scrollToElement(doNotAcceptButton);
        waitForVisibilityAndClick(doNotAcceptButton);
        doNotAcceptButton.should(disappear);
    }


    public void switchToCookiesSetting() {
        SelenideElement cookiesSettingButton = $(By.id("onetrust-pc-btn-handler"));
        scrollToElement(cookiesSettingButton);
        waitForVisibilityAndClick(cookiesSettingButton);
        //cookiesSettingButton.shouldNot(clickable);

    }
}
