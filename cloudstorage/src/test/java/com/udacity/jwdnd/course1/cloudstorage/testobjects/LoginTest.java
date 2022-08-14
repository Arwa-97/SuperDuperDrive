package com.udacity.jwdnd.course1.cloudstorage.testobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginTest {
    @FindBy(id = "inputUsername")
    private WebElement inputUsername;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    public LoginTest(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public String getInputUsername() {
        return inputUsername.getText();
    }

    public String getInputPassword() {
        return inputPassword.getText();
    }
    public void setInputUsername(String username) {
        inputUsername.sendKeys(username);
    }

    public void setInputPassword(String password) {
        inputPassword.sendKeys(password);
    }
    public void loginSubmit(){
        loginButton.click();
    }

    public void clickUsername(){
        inputUsername.click();
    }
    public void clickPassword(){
        inputPassword.click();
    }
}
