package org.pageObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.base.BaseClass;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.utility.UtilityClass;

public class CheckoutPage extends BaseClass {

	UtilityClass utilityClass;
	CartPojo cartPojo;

	public CheckoutPage() {
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "finish")
	public WebElement finishButton;

	@FindBy(xpath = "//h2[@class='complete-header']")
	public WebElement orderSuccessMessage;

	public void verifyCartTotalIsInRequiredFromat() {

		utilityClass = new UtilityClass();
		cartPojo = new CartPojo();

		String cartTotal = utilityClass.getttingText(cartPojo.totalPrice);
		int indexOfSpace = cartTotal.indexOf(' ');
		String substring = cartTotal.substring(indexOfSpace + 1);

		String pattern = "\\$\\d{2}\\.\\d{2}";

		Pattern compile = Pattern.compile(pattern);
		Matcher matcher = compile.matcher(substring);

		boolean matches = matcher.matches();

		if (matches == true) {
			Assert.assertTrue(true);

		} else {
			Assert.assertTrue(false);
		}
	}
	
	public void completeCheckoutAndVerifySuccessMessage() {
		utilityClass.btnClick(finishButton);

		Assert.assertTrue(orderSuccessMessage.getText().contains("Thank you"));

	}
}
