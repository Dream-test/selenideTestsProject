package downloadtestproject.pages;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class UploadResizePixelPage {

    public void isUploadPageLoaded() {
        SelenideElement pageHeader = $("h1.main-text");
        pageHeader.shouldBe(visible).shouldHave(Condition.text("Бесплатный редактор изображений"));
    }

    public SelenideElement getImageInput() {
        SelenideElement imageFileInput = $("input[name='imageFile']");
        imageFileInput.shouldHave(Condition.attribute("type", "file"));
        return imageFileInput;
    }
}
