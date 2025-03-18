package downloadtestproject.pages;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class XlsTemplatesPage {

    public void isXlsTemplatesPageLoaded() {
        SelenideElement downloadHeader = $x("//p[contains(@class, 'H4') and normalize-space(text())='Шаблон файла для импорта отделов']");
        downloadHeader.shouldBe(visible);
    }

    public SelenideElement getDownloadXlsButton() {
        return $$(Selectors.byTagAndText("a", "скачать файл.xlsx")).first();
    }
}
