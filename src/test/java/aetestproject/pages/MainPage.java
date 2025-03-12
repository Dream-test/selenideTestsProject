package aetestproject.pages;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Selenide.$;

public class MainPage {
     public void isMainPageLoaded() {
         ElementsCollection emberList = $(By.id("ember17")).$$(By.cssSelector("img.img-responsive"));
         emberList.shouldHave(CollectionCondition.sizeGreaterThan(0));
         for (SelenideElement img : emberList) {
             img.shouldHave(attribute("src"));
         }

     }


}
