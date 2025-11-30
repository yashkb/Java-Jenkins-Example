package testCases;

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

}
