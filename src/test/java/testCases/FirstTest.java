package testCases;

import static org.testng.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import config.DataProviderConfig;
import core.BaseTest;
import pages.SearchPage;

public class FirstTest extends BaseTest {
	
	@Test(dataProvider = "SearchData",dataProviderClass = DataProviderConfig.class)
	public void TC_001(String searchword)
	{
		logInfo("Starting valid search test");
		SearchPage searchObj = new SearchPage(driver);
		logInfo("Entering search keyword: 'Yash'");
		searchObj.enterSearchKeyword(searchword);
		logInfo("Performing search");
		waitForSeconds(3);
		logPass("Search test completed successfully");
	}
	
	@Test
	public void ValidateSearchDisplayed()
	{
		SearchPage searchObj = new SearchPage(driver);
		logInfo("Validating search results");
		searchObj.enterSearchKeyword("Java");
		waitForSeconds(3);
		List<WebElement> suggestions = searchObj.getSearchSuggestions();
		assertTrue(suggestions.size()>0, "Search suggestions are displayed");
	}
	@Test
	public void ValidateSearchNotDisplayed()
	{
		SearchPage searchObj = new SearchPage(driver);
		logInfo("Validating search results");
		searchObj.enterSearchKeyword("");
		waitForSeconds(1);
		List<WebElement> suggestions = searchObj.getSearchSuggestions();
		System.out.println(suggestions.size());
		assertTrue(suggestions.size()==0, "Search suggestions are not displayed");
	}
	@Test
	public void ValidateSearchCount()
	{
		SearchPage searchObj = new SearchPage(driver);
		searchObj.enterSearchKeyword("HTML");
		waitForSeconds(3);
		List<WebElement> suggestionsList = searchObj.getSearchSuggestions();
		assertTrue(suggestionsList.size()==8,"Count is not equal to 8");
	}

}
