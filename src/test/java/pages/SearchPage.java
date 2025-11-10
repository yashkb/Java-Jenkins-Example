package pages;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.DataProvider;

import core.BasePage;

public class SearchPage extends BasePage {
	public SearchPage(WebDriver driver) {
		super(driver);
	}
	WebElement searchBar = driver.findElement(By.xpath("//input[@name='q']"));
	
	
	public void enterSearchKeyword(String value)
	{
		enterValue(searchBar,value);
	}

}
