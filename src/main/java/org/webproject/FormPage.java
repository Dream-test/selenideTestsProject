package org.webproject;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.io.File;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class FormPage {
    private final SelenideElement pageTitle = $(By.cssSelector("h1.text-center"));
    private final SelenideElement firstNameField = $(By.id("firstName"));
    private final SelenideElement lastNameField =$(By.id("lastName"));
    private final SelenideElement userEmailField = $(By.id("userEmail"));
    private final SelenideElement maleGender = $(By.xpath("//label[@for='gender-radio-1']"));
    private final SelenideElement maleGenderBox = $(By.id("gender-radio-1"));
    private final SelenideElement femaleGender = $(By.xpath("//label[@for='gender-radio-2']"));
    private final SelenideElement femaleGenderBox = $(By.id("gender-radio-2"));
    private final SelenideElement otherGender = $(By.xpath("//label[@for='gender-radio-3']"));
    private final SelenideElement otherGenderBox = $(By.id("gender-radio-3"));
    private final SelenideElement userPhoneNumberField = $(By.id("userNumber"));
    private final SelenideElement dateOfBirthField = $(By.id("dateOfBirthInput"));
    private final SelenideElement subjectsInputField = $(By.id("subjectsInput"));
    private final SelenideElement sportsHobby = $(By.xpath("//label[@for='hobbies-checkbox-1']"));
    private final SelenideElement sportsHobbyBox = $(By.id("hobbies-checkbox-1"));
    private final SelenideElement readingHobby = $(By.xpath("//label[@for='hobbies-checkbox-2']"));
    private final SelenideElement readingHobbyBox = $(By.id("hobbies-checkbox-2"));
    private final SelenideElement musicHobby = $(By.xpath("//label[@for='hobbies-checkbox-3']"));
    private final SelenideElement musicHobbyBox = $(By.id("hobbies-checkbox-3"));
    private final SelenideElement uploadInputPicture = $(By.id("uploadPicture"));
    private final SelenideElement currentAddress = $(By.id("currentAddress"));
    private final SelenideElement stateField = $("#state .css-1hwfws3");
    private final SelenideElement dropStateMenu = $(".css-11unzgr");
    private final SelenideElement firstStateOption = $(By.id("react-select-3-option-0"));
    private final SelenideElement cityField = $("#city .css-1hwfws3");
    private final SelenideElement dropCityMenu = $(".css-11unzgr");
    private final SelenideElement firstCityOption = $(By.id("react-select-4-option-0"));
    private final SelenideElement submitButton = $(By.id("submit"));





    public void formPageLoaded() {
        pageTitle.shouldBe(visible).shouldHave(Condition.text("Practice Form"));
    }

    public void setFirstName(String firstName) {
        firstNameField.shouldBe(visible).setValue(firstName);
    }

    public void setLastName(String lastName) {
        lastNameField.shouldBe(visible).setValue(lastName);
    }

    public void setUserEmail(String userEmail) {
        userEmailField.shouldBe(visible).setValue(userEmail);
    }

    public void setGender(int checkBoxNumber) {
        if (checkBoxNumber == 1) {
            maleGender.click();
            maleGenderBox.shouldHave(Condition.checked);
        } else if (checkBoxNumber == 2) {
            femaleGender.click();
            femaleGenderBox.shouldHave(Condition.checked);
        } else {
            otherGender.click();
            otherGenderBox.shouldHave(Condition.checked);
        }
    }

    public void setUserPhoneNumber(String phoneNumber) {
        userPhoneNumberField.shouldBe(visible).setValue(phoneNumber);
    }

    public void openCalendar() {
        dateOfBirthField.click();
    }

    public void selectFistSubject(String subjectName) {
        subjectsInputField.shouldBe(visible).setValue(subjectName);
        subjectsInputField.pressEnter();
    }

    public void setHobbies(int checkBoxNumber) {
        if (checkBoxNumber == 1) {
            selectSportsHobby();
        } else if (checkBoxNumber == 2) {
            selectSportsHobby();
            selectReadingHobby();
        } else {
            selectSportsHobby();
            selectReadingHobby();
            selectMusicHobby();
        }
    }

    public void uploadFile(File file, String fileName) {
        uploadInputPicture.uploadFile(file);
        uploadInputPicture.shouldHave(value(fileName));
    }

    public void setCurrentAddress(String userAddress) {
        currentAddress.setValue(userAddress);
    }

    public void selectState(String stateName) {
        stateField.click();
        dropStateMenu.shouldBe(visible);
        firstStateOption.click();
    }

    public void selectCity(String cityName) {
        cityField.click();
        dropCityMenu.shouldBe(visible);
        firstCityOption.click();

    }

    public void clickSubmitButton() {
        submitButton.shouldBe(visible).click();
    }

    private void selectSportsHobby() {
        sportsHobby.click();
        sportsHobbyBox.shouldHave(Condition.checked);
    }

    private void selectReadingHobby() {
        readingHobby.click();
        readingHobbyBox.shouldHave(Condition.checked);
    }

    private void selectMusicHobby() {
        musicHobby.click();
        musicHobbyBox.shouldHave(Condition.checked);
    }
}
