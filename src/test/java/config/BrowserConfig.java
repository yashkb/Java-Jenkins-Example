package config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BrowserConfig {
	public static WebDriver createDriver()
	{
		String browser = TestConfig.getBaseUrl();
		
		WebDriver driver = new ChromeDriver();
		
		return driver;
	}
}
