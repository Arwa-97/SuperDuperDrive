package com.udacity.jwdnd.course1.cloudstorage.testobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CredentialTest {
    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialsTab;
    @FindBy(id = "addCredential")
    private WebElement addCredentialBtn;
    @FindBy(id = "credentialModalBtn")
    private WebElement submitCredential;
    @FindBy(id = "credential-url")
    private WebElement credentialUrl;
    @FindBy(id = "credential-username")
    private WebElement credentialUsername;
    @FindBy(id = "credential-password")
    private WebElement credentialPassword;

    public CredentialTest(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
    public void clickCredentialsTab() {
        credentialsTab.click();
    }
    public void clickAddCredential() {
        addCredentialBtn.click();
    }
    public void submitCredential() {
        submitCredential.click();
    }
    public void clickCredentialUrl() {
        credentialUrl.click();
    }
    public void clearCredentialUrl() {
        credentialUrl.clear();
    }
    public void setCredentialUrl(String url) {
        credentialUrl.sendKeys(url);
    }
    public void clickCredentialUsername() {
        credentialUsername.click();
    }
    public void clearCredentialUsername() {
        credentialUsername.clear();
    }
    public void setCredentialUsername(String username) {
        credentialUsername.sendKeys(username);
    }
    public void clickCredentialPassword() {
        credentialPassword.click();
    }
    public void clearCredentialPassword() {
        credentialPassword.clear();
    }
    public void setCredentialPassword(String password) {
        credentialPassword.sendKeys(password);
    }
}
