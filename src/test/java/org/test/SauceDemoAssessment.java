package org.test;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.base.BaseClass;
import org.dataprovider.DataProvidersTestData;
import org.utility.UtilityClass;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.pageObject.CartPojo;
import org.pageObject.CheckoutPage;
import org.pageObject.HomePojo;
import org.pageObject.LoginPojo;
import org.pageObject.ProductPojo;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class SauceDemoAssessment extends BaseClass {

	LoginPojo loginPojo;
	HomePojo homePojo;
	ProductPojo productPojo;
	CartPojo cartPojo;
	CheckoutPage checkoutPage;
	UtilityClass utilityClass;

	@BeforeSuite
	public void setup() {

		String name = propertiesFileDataAccess("test_name");
		String description = propertiesFileDataAccess("description");
		generateExtentReport(name, description);

	}

	@BeforeClass
	public void startLogin() {

		test.log(Status.INFO, "Starting test case for Sauce Demo site.");
		setupBrowserAndLaunchURL();

		loginPojo = new LoginPojo();
		homePojo = new HomePojo();
		productPojo = new ProductPojo();
		cartPojo = new CartPojo();
		checkoutPage = new CheckoutPage();
		utilityClass = new UtilityClass();

	}

	@Test(dataProvider = "Credentials", dataProviderClass = DataProvidersTestData.class, description = "Multiple crendentail Combinations validation.")
	public void tc1(String user, String pass) {

		boolean loginStatus = homePojo.verifyLoginIsCompletedAndVerified(user, pass);
		if (loginStatus == true) {
			homePojo.performLogoutOfPage();
		}
	}

	@Parameters({ "username", "password" })
	@Test(description = "Valid Login success and about us page launch")
	public void tc2(String user, String pass) {

		homePojo.verifyLoginIsCompletedAndVerified(user, pass);

		homePojo.navigateToAboutUsPageAndVerify();

		test.pass("Navigated to About us page after successful login");
		
		browserBackwardNavigation();

	}

	@Test(description = "Adding highest value Product to the cart page")
	public void tc3() {

		homePojo.verifyHomePageIsDisplayed();

		productPojo.performProductFilterOption();

		productPojo.addFirstProductToCart();

		cartPojo.navigateToCartPage();

		test.pass("Product with highest price is added to cart page");

	}

	@Parameters({ "firstName", "lastName", "zipcode" })
	@Test(description = "Verify checkout page contains total price in $xx.yy format")
	public void tc4(String firstName, String lastName, String zipcode) {

		cartPojo.navigateToCheckoutPage();

		cartPojo.enterUserInfoToCompleteCheckout();

		checkoutPage.verifyCartTotalIsInRequiredFromat();

		test.pass("Verified the price format is according to $xx.yy format");

	}

	@Test(description = "Verify order successful messgae")
	public void tc5() {

		checkoutPage.completeCheckoutAndVerifySuccessMessage();

		test.pass("Verified the Successful order placement");

	}

	@Test(description = "Logged out successfully")
	public void tc6() {
		homePojo.performLogoutOfPage();

		test.pass("Successfully logged out");

	}

	@AfterClass
	public void closing() {
		closeTab();
		closeBrowser();

		test.log(Status.INFO, "Ending Test for Sauce Demo site.");

	}

	@AfterSuite
	public void ending() {
		extentReports.flush();

	}

}
