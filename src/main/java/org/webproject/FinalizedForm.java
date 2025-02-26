package org.webproject;

import com.codeborne.selenide.*;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class FinalizedForm {

    private final SelenideElement formTitle = $(By.id("example-modal-sizes-title-lg"));
    private final ElementsCollection tableRows = $$(Selectors.byTagName("tr")).shouldBe(CollectionCondition.sizeGreaterThan(9));
    private final SelenideElement closeButton = $(By.id("closeLargeModal"));

    private String getTableValue(String label) {

        return tableRows.stream()
                .map(row -> row.$$(Selectors.byTagName("td"))) // Собираю все <td> в каждой строке
                .filter(cells -> !cells.isEmpty() && cells.get(0).getText().equals(label)) // Проверяю, что список не пуст и label совпадает
                .findFirst()
                .map(cells -> cells.get(1).getText()) // Беру второй <td> с данными
                .orElseThrow(() -> new RuntimeException("Row with '" + label + "' not found"));
    }


    public void isFinalFormLoaded() {
        formTitle.shouldBe(visible).shouldHave(Condition.text("Thanks for submitting the form"));
    }

    public String getFormUserName() {
        return getTableValue("Student Name");
    }

    public String getFormUserEmail() {
        return getTableValue("Student Email");
    }

    public String getFormUserGender() {
        return getTableValue("Gender");
    }

    public String getFormUserMobileNumber() {
        return getTableValue("Mobile");
    }

    public String getFormUserBirthday() {
        return getTableValue("Date of Birth");
    }

    public String getFormUserSubjects() {
        return getTableValue("Subjects");
    }

    public String getFormUserHobbies() {
        return getTableValue("Hobbies");
    }

    public String getFormUserPicture() {
        return getTableValue("Picture");
    }

    public String getFormUserAddress() {
        return getTableValue("Address");
    }

    public String getFormUserStateAndCity() {
        return getTableValue("State and City");
    }

    public void closeForm() {
        closeButton.click();
        formTitle.shouldBe(disappear);
    }
}
