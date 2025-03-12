package aetestproject.modaldialogs;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class CookieSettingsDialog {

    public void isCookieSettingsDialogLoaded() {
        $(By.id("close-pc-btn-handler")).shouldBe(visible).shouldBe(clickable);
    }

    public void closeCookieSettingsDialog() {
        SelenideElement closeSettingsDialogButton =$(By.id("close-pc-btn-handler"));
        closeSettingsDialogButton.shouldBe(visible).click();
        closeSettingsDialogButton.should(disappear);
    }

    public void allowAllCookies() {
        SelenideElement allowAllButton =$(By.id("accept-recommended-btn-handler"));
        allowAllButton.scrollIntoView(false).shouldBe(visible);
        allowAllButton.shouldBe(visible).click();

    }

    public void rejectAllCookies() {
        SelenideElement rejectAllButton = $(By.cssSelector("button.ot-pc-refuse-all-handler"));
        rejectAllButton.shouldBe(visible).click();
        rejectAllButton.should(disappear);
    }

    public void confirmCookieChoices() {
        SelenideElement confirmChoicesButton = $(By.cssSelector("button.save-preference-btn-handler"));
        confirmChoicesButton.shouldBe(visible).click();
        confirmChoicesButton.should(disappear);
    }


}
