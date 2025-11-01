package testCases;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;


import core.BaseTest;
import pages.SearchPage;

public class FirstTest extends BaseTest {
	
	@Test
	public void TC_001()
	{
		logInfo("Starting valid search test");
		SearchPage searchObj = new SearchPage(driver);
		logInfo("Entering search keyword: 'Yash'");
		searchObj.enterSearchKeyword("Yash");
		logInfo("Performing search");
		waitForSeconds(3);
		logPass("Search test completed successfully");
	}

}
