package aetestproject.tests;

import aetestproject.modaldialogs.*;
import aetestproject.pageelements.ShopNowTodayOffersMenu;
import aetestproject.pageelements.TopMenuElements;
import aetestproject.pages.*;
import aetestproject.utils.RandomGenerator;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class CartTests {
    private static final Logger logger = LoggerFactory.getLogger(CartTests.class);
    private final String url = "https://www.ae.com/us/en";

    @BeforeAll
    public static void configurationBrowser() {
        Configuration.browserSize = "1366x768";
        Configuration.headless = false;
        Configuration.timeout = 10000;
    }

    @BeforeEach
    public void openMainPage() {
        open(url);
        sleep(5000);
        CookieAcceptDialog cookieAcceptDialog = new CookieAcceptDialog();
        if (cookieAcceptDialog.isCookieAcceptDialogPresent()) {
            cookieAcceptDialog.isCookieAcceptDialogLoaded();
            cookieAcceptDialog.switchToCookiesSetting();
            CookieSettingsDialog cookieSettingsDialog = new CookieSettingsDialog();
            cookieSettingsDialog.allowAllCookies();
        }

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
    public void addProductToBagAndSelectPaypalPayment() {
        RandomGenerator randomGenerator = new RandomGenerator();
        MainPage mainPage = new MainPage();
        mainPage.isMainPageLoaded();

        if (ShippingModalDialog.isShippingDialogPresent()) {
            ShippingModalDialog.getFirstShippingTo();
        }

        TopMenuElements topMenuElements = new TopMenuElements();
        topMenuElements.getTodayOffersModalDialog();

        TodayOfferModalDialog todayOfferModalDialog = new TodayOfferModalDialog();
        todayOfferModalDialog.isTodayOfferDialogPresent();
        int todayOfferQuantity = todayOfferModalDialog.getOfferQuantity();

        int todayOfferOption = randomGenerator.getOption(todayOfferQuantity);
        todayOfferModalDialog.clickSelectedOffer(todayOfferOption);

        ShopNowTodayOffersMenu shopNowTodayOffersMenu = new ShopNowTodayOffersMenu();
        shopNowTodayOffersMenu.isShopNowTodayOfferMenuPresent();
        int shopNowOptionsQuantity = shopNowTodayOffersMenu.getShopNowTodayOfferMenuQuantity();

        int shopNowOption = randomGenerator.getOption(shopNowOptionsQuantity);
        shopNowTodayOffersMenu.clickSelectedMenuOption(shopNowOption);

        sleep(3000);

        if (ShippingModalDialog.isShippingDialogPresent()) {
            ShippingModalDialog.getFirstShippingTo();
        }

        OfferPage offerPage = new OfferPage();
        offerPage.isOfferPageLoaded();
        offerPage.clickFirstOffer();

        ProductPage productPage = new ProductPage();
        productPage.isProductPageLoaded();
        productPage.selectFirstSize();
        productPage.clickAddToCartButton();

        sleep(2000);

        AddedToBagDialog addedToBagDialog = new AddedToBagDialog();
        addedToBagDialog.isAddedToBagDialogPresent();
        addedToBagDialog.clickViewBagButton();

        ShoppingBagPage shoppingBagPage = new ShoppingBagPage();
        shoppingBagPage.isShoppingBagPageLoaded();
        shoppingBagPage.clickPayPalButton();

        Set<String> windows = getWebDriver().getWindowHandles();
        logger.info("Windows list: {}", windows);
        String originalWindow = windows.stream().toList().getFirst();
        switchTo().window(windows.stream().toList().get(1));

        PayPalPage payPalPage= new PayPalPage();
        payPalPage.isPayPalPageLoaded();

        closeWindow();
        switchTo().window(originalWindow);

        shoppingBagPage.checkPaypalMessageNotExist();

        sleep(1000);

    }

}
