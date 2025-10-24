package core;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import config.BrowserConfig;
import config.TestConfig;

public class BaseTest {
	protected WebDriver driver;
	@BeforeClass
	public void setup()
	{
		System.out.println(driver);
		driver = BrowserConfig.createDriver();
		driver.get(TestConfig.getBaseUrl());
	}
	
	public void waitForSeconds(int seconds) {
        System.out.println("⏳ Waiting " + seconds + " seconds...");
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
	
	@AfterClass
	public void tearDown()
	{
		driver.quit();
	}

}
