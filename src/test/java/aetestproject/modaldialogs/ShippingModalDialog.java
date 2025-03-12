package aetestproject.modaldialogs;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Selenide.$;

public class ShippingModalDialog {

    public static boolean isShippingDialogPresent() {
        SelenideElement shippingDialogBody = $(By.id("ember43"));
        return shippingDialogBody.isDisplayed();
    }

    public static void getFirstShippingTo() {
        SelenideElement shippingDialogBody = $(By.id("ember43"));
        ElementsCollection shipToDestination = $(By.className("_flags-wrapper_1lzwmt")).$$(By.tagName("div"));
        shipToDestination.first().click();
        shippingDialogBody.should(disappear);
    }
}
