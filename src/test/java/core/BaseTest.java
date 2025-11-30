package core;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentTest;

import config.BrowserConfig;
import config.ExtentManager;
import config.TestConfig;

public class BaseTest {
	protected WebDriver driver;
	
	protected ThreadLocal<ExtentTest> test = new ThreadLocal<>();
	
	@BeforeSuite
    public void globalSetup() {
        System.out.println("🚀 [BaseTest] Initializing ExtentReports...");
        ExtentManager.getInstance(); // Initializes reporting framework
        System.out.println("✅ [BaseTest] ExtentReports ready");
    }
	
	@BeforeClass
	public void setup()
	{
		
		
		
	}
	
	@BeforeMethod
    public void methodSetup(ITestResult result) {
		System.out.println(driver);
		driver = BrowserConfig.createDriver();
		driver.get(TestConfig.getBaseUrl());
        String testName = result.getMethod().getMethodName();
        System.out.println("▶️ [BaseTest] Starting test: " + testName);
        
        // WHY: Creates a new test entry in the report for this specific test method
        // HOW: Uses method name as test name in reports for clear identification
        ExtentTest extentTest = ExtentManager.getInstance().createTest(testName);
        test.set(extentTest);           // Stores in ThreadLocal for thread safety
        ExtentManager.setTest(extentTest); // Stores in manager for global access
        
        // WHY: Logs test start with context information for better traceability
        // HOW: Uses helper methods that safely handle logging even if ExtentTest is unavailable
        logInfo("Starting test: " + testName);
        logInfo("URL: " + driver.getCurrentUrl());
        //logInfo("Environment: " + TestConfig.getEnvironment());
        logInfo("Browser: " + TestConfig.getBrowser());
    }
	
	@AfterMethod
    public void methodTeardown(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        System.out.println("🔧 [BaseTest] Finishing test: " + testName);
        
        ExtentTest currentTest = test.get();
        if (currentTest != null) {
            // WHY: Records test outcome in the report based on actual execution result
            // HOW: Uses TestNG ITestResult status to determine pass/fail/skip
            switch (result.getStatus()) {
                case ITestResult.SUCCESS:
                    currentTest.pass("✅ Test passed successfully");
                    break;
                case ITestResult.FAILURE:
                    currentTest.fail("❌ Test failed: " + result.getThrowable().getMessage());
                    break;
                case ITestResult.SKIP:
                    currentTest.skip("⏸️ Test skipped");
                    break;
            }
            
            // WHY: Adds performance metrics to help identify slow tests
            // HOW: Calculates execution time from TestNG's start and end timestamps
            long duration = result.getEndMillis() - result.getStartMillis();
            currentTest.info("⏱️ Execution time: " + duration + "ms");
        }
        
        // WHY: Cleanup to prevent memory leaks and prepare for next test
        // HOW: Removes test instances from both ThreadLocal and ExtentManager
        ExtentManager.removeTest();
        test.remove();
        driver.quit();
    }
	
	public void waitForSeconds(int seconds) {
        System.out.println("⏳ Waiting " + seconds + " seconds...");
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
	
	@AfterSuite
    public void globalTeardown() {
        System.out.println("🏁 [BaseTest] Generating reports...");
        ExtentManager.flushReports(); // Generates the final HTML report file
        System.out.println("✅ [BaseTest] Reports generated successfully");
    }
	protected void logInfo(String message) {
        if (test.get() != null) {
            test.get().info(message); // Logs to ExtentReports
        } else {
            System.out.println("📝 " + message); // Fallback to console
        }
    }
	
	@AfterClass
	public void tearDown()
	{
		
	}
	
	protected void logPass(String message) {
        if (test.get() != null) {
            test.get().pass("✅ " + message); // Green checkmark in reports
        } else {
            System.out.println("✅ " + message); // Checkmark in console
        }
    }
	protected void logFail(String message) {
        if (test.get() != null) {
            test.get().fail("❌ " + message); // Red cross in reports
        } else {
            System.out.println("❌ " + message); // Cross in console
        }
    }
	protected void logWarning(String message) {
        if (test.get() != null) {
            test.get().warning("⚠️ " + message); // Yellow warning in reports
        } else {
            System.out.println("⚠️ " + message); // Warning in console
        }
    }

}
