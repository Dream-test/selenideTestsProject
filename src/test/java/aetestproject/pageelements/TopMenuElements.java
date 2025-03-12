package aetestproject.pageelements;

import static com.codeborne.selenide.Selenide.$;

public class TopMenuElements {

    public void getTodayOffersModalDialog() {
        $("button[name='showFeaturedOffers']").click();
    }
}
