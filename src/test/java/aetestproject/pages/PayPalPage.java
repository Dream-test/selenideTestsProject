package aetestproject.pages;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class PayPalPage {

    public void isPayPalPageLoaded() {
        $("div#content").shouldBe(visible);
    }
}
