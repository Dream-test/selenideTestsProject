package aetestproject.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.Assertions;

import java.time.Duration;

import static aetestproject.utils.CustomActions.scrollToElement;
import static aetestproject.utils.CustomActions.waitForVisibilityAndClick;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.switchTo;

public class ShoppingBagPage {

    public void isShoppingBagPageLoaded() {
        $("h1.page-header").shouldBe(visible).shouldHave(Condition.text("Shopping Bag"));
        $("div.cart-order-summary").shouldBe(visible);
    }

    public void clickPayPalButton() {
        String originalWindow = WebDriverRunner.getWebDriver().getWindowHandle();

        SelenideElement payPalIframe = $("iframe[title='PayPal']");
        scrollToElement(payPalIframe);

        switchTo().frame(payPalIframe);
        waitForVisibilityAndClick($("div.paypal-button"));

        switchTo().window(originalWindow);

        SelenideElement payPalCheckoutIframe = $("iframe.paypal-checkout-sandbox-iframe").should(Condition.exist, Duration.ofSeconds(10));

        switchTo().frame(payPalCheckoutIframe);
        SelenideElement payPalCheckoutMessage = $("div.paypal-checkout-message");
        payPalCheckoutMessage.shouldBe(visible);
        Assertions.assertTrue(payPalCheckoutMessage.getText().contains("Не отображается безопасная страница paypal в браузере?"));
    }

    public void checkPaypalMessageNotExist() {
        $("iframe.paypal-checkout-sandbox-iframe").shouldNot(Condition.exist);
    }
}
