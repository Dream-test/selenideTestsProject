package org.webproject;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import static com.codeborne.selenide.Selenide.*;

public class FormTests {
    final private Faker faker = new Faker();
    private final String url = "https://demoqa.com/automation-practice-form";

    @BeforeAll
    public static void configurationBrowser() {
        Configuration.browserSize = "1366x768";
        Configuration.headless = true;
        Configuration.timeout = 10000;
    }

    @BeforeEach
    public void openMainPage() {
        open(url);
        executeJavaScript("document.body.style.zoom='50%'");
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
    public void fillFormTest() {
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String userEmail = faker.internet().emailAddress();
        String phoneNumber = faker.number().digits(10);
        String birthYear = "2023";
        String birthMonth = "3";
        String capsBirthMonth = "April";
        String birthDay = "14";
        String subjectName = "M"; //Subject "Math"
        int genderCheckQuantity = 3;
        int hobbiesCheckQuantity = 3;
        String userAddress = faker.address().fullAddress();
        String stateName = "NCR"; //react-select-3-option-0
        String cityName = "Delhi"; //react-select-4-option-0

        FormPage formPage = new FormPage();
        formPage.formPageLoaded();
        formPage.setFirstName(firstName);
        formPage.setLastName(lastName);
        formPage.setUserEmail(userEmail);

        RandomGenerator randomGenerator = new RandomGenerator();
        int genderCheckNumber = randomGenerator.checkBoxNumber(genderCheckQuantity);
        formPage.setGender(genderCheckNumber);

        formPage.setUserPhoneNumber(phoneNumber);

        formPage.openCalendar();
        CalendarElements calendarElements = new CalendarElements();
        calendarElements.ifCalendarVisible();
        calendarElements.setUserBirthYear(birthYear);
        calendarElements.setUserBirthMonth(birthMonth);
        calendarElements.selectFourteenthDay();

        formPage.selectFistSubject(subjectName);

        int hobbyCheckNumber = randomGenerator.checkBoxNumber(hobbiesCheckQuantity);
        formPage.setHobbies(hobbyCheckNumber);

        java.io.File file = new java.io.File("src/test/java/resources/sample.png");
        String fileName = "sample.png";
        formPage.uploadFile(file, fileName);

        formPage.setCurrentAddress(userAddress);

        formPage.selectState(stateName);
        formPage.selectCity(cityName);

        formPage.clickSubmitButton();

        FinalizedForm finalizedForm = new FinalizedForm();
        finalizedForm.isFinalFormLoaded();

        String formUserName = finalizedForm.getFormUserName();
        assertThat(formUserName).contains(firstName);
        assertThat(formUserName).contains(lastName);

        assertThat(finalizedForm.getFormUserEmail()).contains(userEmail);

        if (genderCheckNumber == 1) {
            assertThat(finalizedForm.getFormUserGender()).contains("Male");
        } else if (genderCheckNumber == 2) {
            assertThat(finalizedForm.getFormUserGender()).contains("Female");
        } else {
            assertThat(finalizedForm.getFormUserGender()).contains("Other");
        }

        assertThat(finalizedForm.getFormUserMobileNumber()).contains(phoneNumber);

        String formUserBirthday = finalizedForm.getFormUserBirthday();
        assertThat(formUserBirthday).contains(birthYear);
        assertThat(formUserBirthday).contains(capsBirthMonth);
        assertThat(formUserBirthday).contains(birthDay);

        assertThat(finalizedForm.getFormUserSubjects()).contains("Maths");

        String userHobbies = finalizedForm.getFormUserHobbies();
        List<String> expectedHobbies = new ArrayList<>();
        expectedHobbies.add("Sports");

        if (hobbyCheckNumber >= 2) {
            expectedHobbies.add("Reading");
        }
        if (hobbyCheckNumber == 3) {
            expectedHobbies.add("Music");
        }

        expectedHobbies.forEach(hobby -> assertThat(userHobbies).contains(hobby));

        assertThat(finalizedForm.getFormUserPicture()).contains(fileName);

        assertThat(finalizedForm.getFormUserAddress()).contains(userAddress);

        String formStateAndCity = finalizedForm.getFormUserStateAndCity();
        assertThat(formStateAndCity).contains(stateName);
        assertThat(formStateAndCity).contains(cityName);

        finalizedForm.closeForm();
    }
}
