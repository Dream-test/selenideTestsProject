package aetestproject.modaldialogs;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static aetestproject.utils.CustomActions.waitForVisibilityAndClick;
import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class AddedToBagDialog {
    private SelenideElement dialogContent;

    public void isAddedToBagDialogPresent() {
        this.dialogContent = $("div.modal-content");
        dialogContent.shouldBe(visible);
        dialogContent.$("h2.modal-title")
                .shouldBe(visible)
                .shouldHave(Condition.text("Added to bag!"));
    }

    public void clickViewBagButton() {
        waitForVisibilityAndClick(dialogContent.$("button[name='viewBag']"));
        dialogContent.should(disappear);
    }

    public void clickKeepShoppingButton() {
        waitForVisibilityAndClick(dialogContent.$("button[name='keepShopping']"));
        dialogContent.should(disappear);
    }
}
