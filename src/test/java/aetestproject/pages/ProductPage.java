package aetestproject.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static aetestproject.utils.CustomActions.scrollToElement;
import static aetestproject.utils.CustomActions.waitForVisibilityAndClick;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class ProductPage {

    public void isProductPageLoaded() {
        $("._extras-label_kfr7e8").shouldBe(visible);
        $(Selectors.byTagAndText("div", "Price:")).shouldBe(visible);
    }

    public void selectFirstSize() {
        SelenideElement selectSizeDropAct = $(Selectors.byTagAndText("span", "Size"));
        scrollToElement(selectSizeDropAct);
        selectSizeDropAct.click();

        SelenideElement dropSizeMenu = $("ul.dropdown-menu");
        dropSizeMenu.shouldBe(visible);
        ElementsCollection sizeMenuOptions = dropSizeMenu.$$(By.tagName("li"));
        sizeMenuOptions.stream()
                .filter(option -> !option.getAttribute("class").contains("visually-disabled"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No enabled size option found"))
                .click();
    }

    public void clickAddToCartButton() {
        waitForVisibilityAndClick($("button.qa-btn-add-to-bag"));
    }
}
