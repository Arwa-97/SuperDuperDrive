package com.udacity.jwdnd.course1.cloudstorage.testobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupTest {

    @FindBy(id = "inputFirstName")
    private WebElement inputFirstName;

    @FindBy(id = "inputLastName")
    private WebElement inputLastName;

    @FindBy(id = "inputUsername")
    private WebElement inputUsername;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(id = "buttonSignUp")
    private WebElement buttonSignUp;
    @FindBy(id="success-msg")
    private WebElement successSignupTxt;

    public SignupTest(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
    public String getInputFirstName(){
        return this.inputFirstName.getText();
    }
    public void setInputFirstName(String firstName){
        this.inputFirstName.sendKeys(firstName);
    }

    public String getInputLastName(){
        return inputLastName.getText();
    }
    public void setInputLastName(String lastName){
        inputLastName.sendKeys(lastName);
    }

    public String getInputUsername(){
        return inputUsername.getText();
    }
    public void setInputUsername(String username){
        inputUsername.sendKeys(username);
    }

    public String getInputPassword(){
        return inputPassword.getText();
    }
    public void setInputPassword(String password){
        inputPassword.sendKeys(password);
    }

    public void submit(){
        buttonSignUp.click();
    }

    public String getSuccessMessage(){
        return successSignupTxt.getText();
    }
    public void clickFirstName(){
        inputFirstName.click();
    }
    public void clickLastName(){
        inputLastName.click();
    }
    public void clickUsername(){
        inputUsername.click();
    }
    public void clickPassword(){
        inputPassword.click();
    }
}
