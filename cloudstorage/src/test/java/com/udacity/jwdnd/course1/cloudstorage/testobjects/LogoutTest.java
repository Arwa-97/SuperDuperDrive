package com.udacity.jwdnd.course1.cloudstorage.testobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LogoutTest {
    @FindBy(id = "logoutBtn")
    private WebElement logoutBtn;

    public LogoutTest(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void logoutSubmit(){
        logoutBtn.click();
    }

}
