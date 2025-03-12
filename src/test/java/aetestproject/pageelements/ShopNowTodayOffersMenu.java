package aetestproject.pageelements;

import com.codeborne.selenide.ElementsCollection;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class ShopNowTodayOffersMenu {
    public void isShopNowTodayOfferMenuPresent() {
        $(By.className("_collapsed-body_q8p6b4")).shouldBe(visible);
    }

    public int getShopNowTodayOfferMenuQuantity() {
        return $(By.className("_collapsed-body_q8p6b4")).$$(By.tagName("a")).size();
    }

    public void clickSelectedMenuOption(int optionNumber) {
        ElementsCollection menuOptions = $(By.className("_collapsed-body_q8p6b4")).$$(By.tagName("a"));
        if (optionNumber <= menuOptions.size()) {
            menuOptions.get(optionNumber).click();
        }
    }
}
