package org.pageObject;

import java.util.List;

import org.base.BaseClass;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.utility.UtilityClass;

public class ProductPojo extends BaseClass {

	UtilityClass utilityClass;

	public ProductPojo() {
		PageFactory.initElements(driver, this);
	}

	@FindBy(className = "product_sort_container")
	public WebElement productSorting;

	@FindBy(xpath = "//div[@class='inventory_item']//div[@class='inventory_item_name ']")
	public List<WebElement> allProducts;

	@FindBy(xpath = "//button[contains(@id,'cart')]")
	public WebElement addToCartButton;

	public void performProductFilterOption() {
		Select se = new Select(productSorting);
		se.selectByValue(propertiesFileDataAccess("filter"));

	}

	public void addFirstProductToCart() {

		utilityClass = new UtilityClass();

		List<WebElement> allProductsList = allProducts;
		utilityClass.btnClick(allProductsList.get(0));

		utilityClass.btnClick(addToCartButton);

	}

}
