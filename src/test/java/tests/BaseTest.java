package tests;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import context.WebDriverContext;
  
 

public class BaseTest {
	
	@BeforeMethod(alwaysRun = true)
	public void setUp() {
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		WebDriverContext.setDriver(driver);		
	}
	
	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		WebDriverContext.teardriver();
	}

}
