package pages;

import java.time.Duration;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.net.UrlChecker.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;

import core.BasePage;

public class SearchPage extends BasePage {
	public SearchPage(WebDriver driver) {
		super(driver);
	}
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	private By searchBar = By.name("q");
	private By suggestionItems = By.xpath("//ul[contains(@class,'searchbox_suggestionList')]//li");
	
	public void enterSearchKeyword(String value)
	{
		try {
			WebElement searchInput = wait.until(ExpectedConditions
			        .visibilityOfElementLocated(searchBar));
			searchInput.clear();
			searchInput.sendKeys(value);
		} catch (org.openqa.selenium.TimeoutException e) {
			// TODO: handle exception
			throw new RuntimeException("Search bar time out",e);
			
		}
		
	}
	public List<WebElement> getSearchSuggestions() {
		try {
			return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(suggestionItems));
		} catch (org.openqa.selenium.TimeoutException e) {
			// TODO: handle exception
			return List.of();
		}
		
    }

}
