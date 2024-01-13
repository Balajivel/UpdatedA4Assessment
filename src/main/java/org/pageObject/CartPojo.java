package org.pageObject;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.base.BaseClass;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.utility.UtilityClass;

public class CartPojo extends BaseClass {

	UtilityClass utilityClass;
	HomePojo homePojo;

	public CartPojo() {
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[contains(@id,'cart')]")
	public WebElement cartButton;

	@FindBy(id = "checkout")
	public WebElement checkoutButton;

	@FindBy(id = "first-name")
	public WebElement firstNameField;

	@FindBy(id = "last-name")
	public WebElement lastNameField;

	@FindBy(id = "postal-code")
	public WebElement postalCode;

	@FindBy(id = "continue")
	public WebElement continueButton;

	@FindBy(xpath = "//div[contains(@class,'summary_total')]")
	public WebElement totalPrice;

	public void navigateToCartPage() {

		utilityClass = new UtilityClass();

		utilityClass.btnClick(cartButton);

		String gettingURL = gettingCurrentURL();

		Assert.assertTrue(gettingURL.contains("cart"));

	}

	public void navigateToCheckoutPage() {

		homePojo = new HomePojo();

		utilityClass.btnClick(checkoutButton);

		String getttingText1 = UtilityClass.getttingText(homePojo.pageTitle);

		Assert.assertEquals("Checkout: Your Information", getttingText1);

	}

	public void enterUserInfoToCompleteCheckout() {

		String textFirstName;
		try {
			textFirstName = readExcel("FirstName");

			String textlastName = readExcel("LastName");
			String pincode = readExcel("Pincode");

			utilityClass.fillText(firstNameField, textFirstName);
			utilityClass.fillText(lastNameField, textlastName);
			utilityClass.fillText(postalCode, pincode);
		} catch (IOException e) {
			e.printStackTrace();
		}
		utilityClass.btnClick(continueButton);

		String getttingText2 = utilityClass.getttingText(homePojo.pageTitle);

		Assert.assertEquals("Checkout: Overview", getttingText2);

	}

}
