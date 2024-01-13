package org.utility;

import org.base.BaseClass;
import org.openqa.selenium.WebElement;

public class UtilityClass extends BaseClass{
	
	public static void fillText(WebElement ele,String value) {
		ele.sendKeys(value);
		

	}
	
	public static void btnClick(WebElement cli) {
		cli.click();

	}
	
	public static void gettingAttribute(WebElement ele, String name) {
		ele.getAttribute(name);

	}
	
	public static String getttingText(WebElement ele) {
		String text = ele.getText();
		return text;

	}
	
	

}
