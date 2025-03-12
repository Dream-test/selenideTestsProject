package aetestproject.pages;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;

import static com.codeborne.selenide.Condition.clickable;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class OfferPage {

    public void isOfferPageLoaded() {
        $("h3.product-name").shouldBe(visible).shouldBe(clickable);
    }

    public void clickFirstOffer() {
        ElementsCollection allOffer = $$("h3.product-name");
        allOffer.should(CollectionCondition.sizeGreaterThanOrEqual(1));
        allOffer.first().click();
    }
}
