package org.webproject;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

public class MenuTests {
    private final String url = "https://the-internet.herokuapp.com/jqueryui/menu";

    @BeforeAll
    public static void configurationBrowser() {
        Configuration.browserSize = "1366x768";
        Configuration.headless = true;
        Configuration.timeout = 10000;
    }

    @BeforeEach
    public void openMainPage() {
        open(url);
    }

    @AfterEach
    public void cleanBrowser() {
        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
    }

    @AfterAll
    public static void tearDownAll() {
        closeWebDriver();
    }

    @Test
    public void expectMenuVisible() {

        MenuPage menuPage = new MenuPage();
        menuPage.queryMenuPageLoaded();
        menuPage.ifDisabledButtonExist();
        menuPage.ifEnabledButtonExist();
    }

    @Test
    public void expectSecondMenuLevel() {
        MenuPage menuPage = new MenuPage();
        menuPage.queryMenuPageLoaded();
        MenuElements menuElements = new MenuElements();
        menuElements.getSecondLevel();
    }

    @Test
    public void expectTransitionToBack(){
        MenuPage menuPage = new MenuPage();
        menuPage.queryMenuPageLoaded();
        MenuElements menuElements = new MenuElements();
        menuElements.getSecondLevel();
        menuElements.transitionToBack();
        JQueryUIPage jQueryUIPage = new JQueryUIPage();
        jQueryUIPage.JQueryUIPageLoaded();
        jQueryUIPage.getMenuButton().click();
        MenuPage secondMenuPage = new MenuPage();
        secondMenuPage.queryMenuPageLoaded();
    }

    @Test
    public void expectThirdMenuLevel() {
        MenuPage menuPage = new MenuPage();
        menuPage.queryMenuPageLoaded();
        MenuElements menuElements = new MenuElements();
        menuElements.getSecondLevel();
        menuElements.getThirdLevel();
    }
}
