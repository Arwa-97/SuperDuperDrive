package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.testobjects.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private LoginTest loginTest;
	private LogoutTest logoutTest;
	private SignupTest signupTest;
	private NoteTest noteTest;
	private CredentialTest credentialTest;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		loginTest = new LoginTest(driver);
		logoutTest = new LogoutTest(driver);
		signupTest = new SignupTest(driver);
		noteTest = new NoteTest(driver);
		credentialTest = new CredentialTest(driver);
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		assertEquals("Login", driver.getTitle());
	}

	/*Login & Signup*/

	private void doUserLogin(String username, String password){
		WebDriverWait driverWait = new WebDriverWait(driver, 15);
		driver.get("http://localhost:" + this.port + "/login");

		driverWait.until(ExpectedConditions.titleContains("Login"));

		driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		loginTest.clickUsername();
		loginTest.setInputUsername(username);

		driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		loginTest.clickPassword();
		loginTest.setInputPassword(password);

		driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		loginTest.loginSubmit();
		driverWait.until(ExpectedConditions.titleContains("Home"));
	}

	private void signup(String firstname, String lastname, String username, String password){
		WebDriverWait webDriverWait = new WebDriverWait(driver, 15);
		driver.get("http://localhost:" + this.port + "/signup");

		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		signupTest.clickFirstName();
		signupTest.setInputFirstName(firstname);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		signupTest.clickLastName();
		signupTest.setInputLastName(lastname);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		signupTest.clickUsername();
		signupTest.setInputUsername(username);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		signupTest.clickPassword();
		signupTest.setInputPassword(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		signupTest.submit();
		webDriverWait.until(ExpectedConditions.titleContains("Login"));
	}

	private void logout(){
		WebDriverWait driverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:"+this.port+"/home");

		driverWait.until(ExpectedConditions.titleContains("Home"));
		driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logoutBtn")));
		logoutTest.logoutSubmit();

		driverWait.until(ExpectedConditions.titleContains("Login"));
		driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className("logoutStatement")));

	}

	private void verifyInaccessibleHomePage(){
		WebDriverWait driverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:"+this.port+"/home");
		assertTrue(driverWait.until(ExpectedConditions.titleContains("Login")));
	}

	@Test
	public void verifyUserSignupAndLogin(){
		signup("a", "a", "a", "123");
		doUserLogin("a", "123");
		logout();
		verifyInaccessibleHomePage();
	}

	@Test
	public void unauthorizedAccess(){
		WebDriverWait driverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:"+this.port+"/home");
		assertTrue(driverWait.until(ExpectedConditions.titleContains("Login")));
	}

	/*Note*/
	@Test
	public void createNote(){
		signup("a", "a", "pop", "123");
		doUserLogin("pop", "123");
		WebDriverWait driverWait = new WebDriverWait(driver, 15);
		driver.get("http://localhost:" + this.port + "/home");

		driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		noteTest.clickNoteTab();

		driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("showNote")));
		noteTest.clickShowNote();

		driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		noteTest.clickNoteTitle();
		noteTest.setNoteTitle("new title");

		driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		noteTest.clickNoteDescription();
		noteTest.setNoteDescription("new description");

		driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteModalBtn")));
		noteTest.submitNote();
		var insertedTitle = driver.findElement(By.xpath("//table[@id=\"userTable\"]//tbody//th[1]")).getText();
		var insertedDescription = driver.findElement(By.xpath("//table[@id=\"userTable\"]//tbody//td[2]")).getText();

		assertEquals("new title", insertedTitle);
		assertEquals("new description", insertedDescription);
	}

	@Test
	public void editNote(){
		signup("a", "a", "a1", "123");
		doUserLogin("a1", "123");
		WebDriverWait driverWait = new WebDriverWait(driver, 15);
		driver.get("http://localhost:" + this.port + "/home");

		driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		noteTest.clickNoteTab();

		driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("showNote")));
		noteTest.clickShowNote();

		driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		noteTest.clickNoteTitle();
		noteTest.setNoteTitle("new title");

		driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		noteTest.clickNoteDescription();
		noteTest.setNoteDescription("new description");

		driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteModalBtn")));
		noteTest.submitNote();

		driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		noteTest.clickNoteTab();
		driver.findElement(By.xpath("//table[@id=\"userTable\"]//tbody//td[1]//button")).click();

		driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		noteTest.clickNoteTitle();
		noteTest.clearNoteTitle();
		noteTest.setNoteTitle("edited");

		driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		noteTest.clickNoteDescription();
		noteTest.clearNoteDescription();
		noteTest.setNoteDescription("edited");

		driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteModalBtn")));
		noteTest.submitNote();

		var insertedTitle = driver.findElement(By.xpath("//table[@id=\"userTable\"]//tbody//th[1]")).getText();
		var insertedDescription = driver.findElement(By.xpath("//table[@id=\"userTable\"]//tbody//td[2]")).getText();

		assertEquals("edited", insertedTitle);
		assertEquals("edited", insertedDescription);
	}
	@Test
	public void deleteNote(){
		signup("a", "a", "a2", "123");
		doUserLogin("a2", "123");
		WebDriverWait driverWait = new WebDriverWait(driver, 15);
		driver.get("http://localhost:" + this.port + "/home");

		driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		noteTest.clickNoteTab();

		driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("showNote")));
		noteTest.clickShowNote();

		driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		noteTest.clickNoteTitle();
		noteTest.setNoteTitle("title");

		driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		noteTest.clickNoteDescription();
		noteTest.setNoteDescription("description");

		driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteModalBtn")));
		noteTest.submitNote();
		driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		noteTest.clickNoteTab();
		driver.findElement(By.xpath("//table[@id=\"userTable\"]//tbody//td[1]//a")).click();

		driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userTable")));

		Assertions.assertThrows(NoSuchElementException.class, () -> {
			driver.findElement(By.xpath("//table[@id=\"userTable\"]//tbody//th[1]"));
			driver.findElement(By.xpath("//table[@id=\"userTable\"]//tbody//td[2]"));
		});
	}

	/*Credential*/
	@Test
	public void createCredential(){
		signup("a", "a", "a14", "123");
		doUserLogin("a14", "123");
		WebDriverWait driverWait = new WebDriverWait(driver, 15);
		driver.get("http://localhost:" + this.port + "/home");

		driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		credentialTest.clickCredentialsTab();

		driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addCredential")));
		credentialTest.clickAddCredential();

		driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		credentialTest.clickCredentialUrl();
		credentialTest.setCredentialUrl("http://localhost:8080/home");

		driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
		credentialTest.clickCredentialUsername();
		credentialTest.setCredentialUsername("arwa");

		driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
		credentialTest.clickCredentialPassword();
		credentialTest.setCredentialPassword("test");

		credentialTest.submitCredential();
		var insertedUrl = driver.findElement(By.xpath("//table[@id=\"credentialTable\"]//tbody//th[1]")).getText();
		var insertedUsername = driver.findElement(By.xpath("//table[@id=\"credentialTable\"]//tbody//td[2]")).getText();
		var insertedPassword = driver.findElement(By.xpath("//table[@id=\"credentialTable\"]//tbody//td[3]")).getText();

		assertEquals("http://localhost:8080/home", insertedUrl);
		assertEquals("arwa", insertedUsername);
		assertNotEquals("test", insertedPassword);
	}
	@Test
	public void editCredential(){
		signup("a", "a", "a114", "123");
		doUserLogin("a114", "123");
		WebDriverWait driverWait = new WebDriverWait(driver, 15);
		driver.get("http://localhost:" + this.port + "/home");

		driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		credentialTest.clickCredentialsTab();

		driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addCredential")));
		credentialTest.clickAddCredential();

		driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		credentialTest.clickCredentialUrl();
		credentialTest.setCredentialUrl("http://localhost:8080/home");

		driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
		credentialTest.clickCredentialUsername();
		credentialTest.setCredentialUsername("arwa");

		driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
		credentialTest.clickCredentialPassword();
		credentialTest.setCredentialPassword("test");

		credentialTest.submitCredential();

		driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		credentialTest.clickCredentialsTab();

		driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTable")));
		driver.findElement(By.xpath("//table[@id=\"credentialTable\"]//tbody//td[1]//button")).click();

		driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		credentialTest.clickCredentialUrl();
		credentialTest.clearCredentialUrl();
		credentialTest.setCredentialUrl("http://localhost:8080/credential");

		driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
		credentialTest.clickCredentialUsername();
		credentialTest.clearCredentialUsername();
		credentialTest.setCredentialUsername("edited");

		driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
		credentialTest.clickCredentialPassword();
		credentialTest.clearCredentialPassword();
		credentialTest.setCredentialPassword("edited");

		credentialTest.submitCredential();

		var insertedUrl = driver.findElement(By.xpath("//table[@id=\"credentialTable\"]//tbody//th[1]")).getText();
		var insertedUsername = driver.findElement(By.xpath("//table[@id=\"credentialTable\"]//tbody//td[2]")).getText();
		var insertedPassword = driver.findElement(By.xpath("//table[@id=\"credentialTable\"]//tbody//td[3]")).getText();

		assertEquals("http://localhost:8080/credential", insertedUrl);
		assertEquals("edited", insertedUsername);
		assertNotEquals("edited", insertedPassword);
	}
	@Test
	public void deleteCredential(){
		signup("a", "a", "lofy", "123");
		doUserLogin("lofy", "123");
		WebDriverWait driverWait = new WebDriverWait(driver, 15);
		driver.get("http://localhost:" + this.port + "/home");

		driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		credentialTest.clickCredentialsTab();

		driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addCredential")));
		credentialTest.clickAddCredential();

		driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		credentialTest.clickCredentialUrl();
		credentialTest.setCredentialUrl("http://localhost:8080/home");

		driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
		credentialTest.clickCredentialUsername();
		credentialTest.setCredentialUsername("arwa");

		driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
		credentialTest.clickCredentialPassword();
		credentialTest.setCredentialPassword("test");

		credentialTest.submitCredential();

		driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		credentialTest.clickCredentialsTab();
		driver.findElement(By.xpath("//table[@id=\"credentialTable\"]//tbody//td[1]//a")).click();

		driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTable")));

		Assertions.assertThrows(NoSuchElementException.class, () -> {
			driver.findElement(By.xpath("//table[@id=\"credentialTable\"]//tbody//th[1]"));
			driver.findElement(By.xpath("//table[@id=\"credentialTable\"]//tbody//td[2]"));
			driver.findElement(By.xpath("//table[@id=\"credentialTable\"]//tbody//td[3]")).getText();
		});
	}



	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 15);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		
		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
		assertTrue(webDriverWait.until(ExpectedConditions.titleContains("Login")));
	}

	
	
	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 15);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling redirecting users 
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric: 
	 * https://review.udacity.com/#!/rubrics/2724/view 
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");

		// Check if we have been redirected to the log in page.
		assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs 
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at: 
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");
		
		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code. 
	 * 
	 * Read more about file size limits here: 
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}



}
