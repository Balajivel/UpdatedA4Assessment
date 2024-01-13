package org.pageObject;

import org.base.BaseClass;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.utility.UtilityClass;

public class HomePojo extends BaseClass {

	LoginPojo loginPojo;
	UtilityClass utilityClass;

	public HomePojo() {
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[contains(@id,'menu-btn')]")
	public WebElement menuButton;

	@FindBy(xpath = "//a[text()='About']")
	public WebElement aboutButton;

	@FindBy(className = "title")
	public WebElement pageTitle;

	@FindBy(id = "logout_sidebar_link")
	public WebElement logoutButton;

	public boolean verifyLoginIsCompletedAndVerified(String user, String pass) {
		loginPojo = new LoginPojo();
		String generatedCurrentUrl = loginPojo.performLoginOperation(user, pass);
		boolean loginStatus = false;
		if (generatedCurrentUrl.contains("inventory")) {
			test.pass("Username: " + user + " ,Password: " + pass + " ->Successful Login");

			loginStatus = true;
		} else {
			test.pass("Username: " + user + " ,Password: " + pass + " -> Error Message: "
					+ loginPojo.errorMessage.getText());
			driver.navigate().refresh();
			loginStatus = false;
		}
		
		
		
		return loginStatus;
	}

	public void navigateToAboutUsPageAndVerify() {

		utilityClass = new UtilityClass();
		utilityClass.btnClick(menuButton);
		utilityClass.btnClick(aboutButton);

		String currentUrl = driver.getCurrentUrl();

		Assert.assertEquals(propertiesFileDataAccess("about_us_url"), currentUrl);

	}

	public void performLogoutOfPage() {
		utilityClass.btnClick(menuButton);
		utilityClass.btnClick(logoutButton);
		String currentUrl = driver.getCurrentUrl();

		Assert.assertTrue(currentUrl.contains("saucedemo"));

	}

	public void verifyHomePageIsDisplayed() {
		String getttingText = utilityClass.getttingText(pageTitle);

		Assert.assertEquals("Products", getttingText);

	}

}
