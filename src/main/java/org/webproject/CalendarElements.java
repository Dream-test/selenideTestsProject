package org.webproject;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class CalendarElements {
    private final SelenideElement popCalendar = $(By.className("react-datepicker-popper"));
    private final SelenideElement yearSelectField = $(By.className("react-datepicker__year-select"));
    private final SelenideElement monthSelectField = $(By.className("react-datepicker__month-select"));
    private final SelenideElement fourteenthDay = $(By.cssSelector(".react-datepicker__day.react-datepicker__day--014"));

    public void ifCalendarVisible() {
        popCalendar.shouldBe(visible);
    }

    public void setUserBirthYear(String birthYear) {
        yearSelectField.selectOptionByValue(birthYear);
    }

    public void setUserBirthMonth(String birthMonth) {
        monthSelectField.selectOptionByValue(birthMonth);
    }

    public void selectFourteenthDay() {
        fourteenthDay.click();
        popCalendar.shouldBe(disappear);
    }
}
