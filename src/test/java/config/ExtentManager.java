package config;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
	private static ExtentReports extent;
	private static final ConcurrentHashMap<Long, ExtentTest> testMap = new ConcurrentHashMap<>();
	private ExtentManager() {
        // Private constructor to prevent instantiation
    }
	public static synchronized ExtentReports getInstance() {
        if (extent == null) {
            initializeExtentReports();
        }
        return extent;
    }
	private static synchronized void initializeExtentReports() {
        // WHY: Unique report filename with timestamp to prevent overwriting
        // HOW: Uses current date-time to create unique report for each test run
        String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
        String reportPath = "test-reports/ExtentReport_" + timeStamp + ".html";
        
        // WHY: SparkReporter generates beautiful HTML reports with charts and graphs
        // HOW: Configures the HTML reporter with path and settings
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        configureSparkReporter(sparkReporter);
        
        // WHY: ExtentReports is the main class that manages all reporting activities
        // HOW: Creates instance and attaches the Spark reporter
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        
        // WHY: Adds environment context to reports for better debugging
        // HOW: Pulls system and test configuration information
        setSystemInfo();
        
        System.out.println("📊 ExtentReports initialized: " + reportPath);
    }
	private static void configureSparkReporter(ExtentSparkReporter spark) {
        // WHY: Document title appears in browser tab and report header
        spark.config().setDocumentTitle("Automation Test Report");
        
        // WHY: Report name helps identify different test execution runs
        //spark.config().setReportName("Test Execution Report - " + TestConfig.getEnvironment());
        
        // WHY: Theme controls colors and styling of the report
        spark.config().setTheme(Theme.STANDARD);
        
        // WHY: Ensures proper character encoding for international text
        spark.config().setEncoding("UTF-8");
        
        // WHY: Consistent timestamp format across the entire report
        spark.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");
    }
	 private static void setSystemInfo() {
	        extent.setSystemInfo("Organization", "Your Company");
	        //extent.setSystemInfo("Environment", TestConfig.getEnvironment()); // Shows dev/QA/prod
	        extent.setSystemInfo("Browser", TestConfig.getBrowser());        // Shows which browser used
	        extent.setSystemInfo("Base URL", TestConfig.getBaseUrl());       // Shows application URL
	        extent.setSystemInfo("OS", System.getProperty("os.name"));       // Shows operating system
	        extent.setSystemInfo("Java Version", System.getProperty("java.version")); // Shows Java version
	        extent.setSystemInfo("User", System.getProperty("user.name"));   // Shows who executed tests
	    }
	 public static void setTest(ExtentTest test) {
	        testMap.put(Thread.currentThread().getId(), test);
	    }
	 public static ExtentTest getTest() {
	        return testMap.get(Thread.currentThread().getId());
	    }
	 public static void removeTest() {
	        testMap.remove(Thread.currentThread().getId());
	    }
	 public static void flushReports() {
	        if (extent != null) {
	            extent.flush();
	            System.out.println("📊 Reports flushed successfully");
	        }
	    }
}
