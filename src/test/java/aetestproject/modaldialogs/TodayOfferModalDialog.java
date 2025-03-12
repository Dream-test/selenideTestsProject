package aetestproject.modaldialogs;

import com.codeborne.selenide.ElementsCollection;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class TodayOfferModalDialog {


    public void isTodayOfferDialogPresent() {
        $(By.className("modal-content")).shouldBe(visible);
    }

    public int getOfferQuantity() {
        ElementsCollection todayOffersList = $(By.className("modal-content")).$$x(".//div[@role='button' and contains(normalize-space(), 'Shop')]");
        return todayOffersList.size();
    }

    public void clickSelectedOffer(int offerNumber) {
        ElementsCollection todayOffersList = $(By.className("modal-content")).$$x(".//div[@role='button' and contains(normalize-space(), 'Shop')]");
        if (offerNumber <= todayOffersList.size()) {
            todayOffersList.get(offerNumber).click();
        }

    }
}
