package downloadtestproject.modaldialogs;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class RPCookiesDialog {

    public boolean isDialogExist() {
        return $("div#cookiescript_header").exists();
    }

    public void acceptAllCookies() {
        SelenideElement acceptAllCookiesButton = $("div#cookiescript_accept");
        acceptAllCookiesButton.shouldBe(visible).shouldBe(clickable).click();
        acceptAllCookiesButton.should(disappear);
    }
}
