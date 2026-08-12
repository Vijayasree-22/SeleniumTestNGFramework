package tests;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import context.WebDriverContext;
import pages.Account;
import pages.Dashboard;
import pages.QAplaygroundLogin;
import utils.LoginData;

public class DashboardTest extends BaseTest {
	
	@Test( 
		testName = "Verify Dashboard Loads Successfully",
		description = "Verify that after a successful login, the Dashboard page loads and displays the welcome message.",
		groups = {"Functional", "Smoke"},
		priority = 1
	)
	public void Verify_Dashboard_Load() {
	
		WebDriver driver = WebDriverContext.getDriver();
		
		QAplaygroundLogin login = new QAplaygroundLogin(driver);
		login.open_QAplayground_Url();
		login.Login_QAplaygroundBank(LoginData.activeUser, LoginData.password);
		
		Dashboard dash = new Dashboard(driver);	
		
		//method 1(verifying the welcome message)
		SoftAssert softassert = new SoftAssert();
		softassert.assertTrue(dash.isWelcomeMessageVisible(), "Dashboard Welcome message is not visible");
		System.out.println("Welcome message in Dashboard: " + dash.getdashboardWelcomeMsg());
		 
		//method 2(verifying the welcome message)
		if(dash.isWelcomeMessageVisible()) {
			System.out.println("Welcome message in Dashboard: " + dash.getdashboardWelcomeMsg());
		} else {
			System.out.println("Dashboard Welcome message is not visible");
		}
		 
		softassert.assertAll();
	}
	
	@Test(
		testName = "Verify Dashboard Total Balance",
	    description = "Verify dashboard total balance matches the sum of account balances.",
	    groups = {"Functional"},
	    dependsOnMethods = {"Verify_Dashboard_Load"},
		priority = 2
	)
	public void Verify_Total_Balance() {
		WebDriver driver = WebDriverContext.getDriver();
		QAplaygroundLogin login = new QAplaygroundLogin(driver);
		Dashboard dash = new Dashboard(driver);
		Account acc = new Account(driver);
		
		login.open_QAplayground_Url();
		login.Login_QAplaygroundBank(LoginData.activeUser, LoginData.password);
		
		Double cardTotal = dash.convertTotalnetworthValue();
		System.out.println("Sum of Accounts Balance : " + cardTotal);
		
		acc.navigateToAccounts();
		
		double sumofAccounts = acc.getAccsBalance();
		
		System.out.println("Sum of Accounts Balance : " + sumofAccounts);
		
		Assert.assertEquals(cardTotal, sumofAccounts, "Total balance does not match.");
	}
	
	@Test(
		testName = "Verify Recent Transactions List Size",
		description = "Verifies that the recent transactions table displays between 0 and 5 rows.",
		groups = {"Functional", "UI"},
		dependsOnMethods = {"Verify_Dashboard_Load"},
		priority = 3
	)
	public void Verify_RecentTransactions_ListSize() {
		WebDriver driver = WebDriverContext.getDriver();	
		QAplaygroundLogin login = new QAplaygroundLogin(driver);
		Dashboard dash = new Dashboard(driver);
		
		login.open_QAplayground_Url();
		login.Login_QAplaygroundBank(LoginData.activeUser, LoginData.password);
		
		int count = dash.recentTrnscCount();		
		
		Assert.assertTrue(count >= 0 && count <= 5,
		        "Row count should be between 0 and 5, but found: " + count);
		
		System.out.println("Recent Transactions count : " + count);
	}

	@Test(
		testName = "Verify Theme Toggle",
		description = "Verifies the application UI successfully toggles between light mode and dark mode.",
		groups = {"Functional", "UI"},
		priority = 4
	)
	public void VerifyThemeToggle() {
		WebDriver driver = WebDriverContext.getDriver();
		QAplaygroundLogin login = new QAplaygroundLogin(driver);
	    Dashboard dash = new Dashboard(driver);

	    login.open_QAplayground_Url();
	    login.Login_QAplaygroundBank(LoginData.activeUser, LoginData.password);
	    	    
	    String currentState = dash.getThemeState();

        dash.clickThemetoggle();
        
        // Wait until aria-label changes
        dash.waitForThemeToChange(currentState);
        
        String updatedtheme = dash.getThemeState();
        
        // Verify toggle worked
        if (currentState.equals("Switch to light mode")) {
            Assert.assertEquals(updatedtheme, "Switch to dark mode", "Theme did not switch to Light mode.");
        } else if (currentState.equals("Switch to dark mode")) {
            Assert.assertEquals(updatedtheme, "Switch to light mode", "Theme did not switch to Dark mode.");
        }
	}
	
	@Test(
		testName = "Verify Transaction Sort Order",
		description = "Ensures that the dates in the recent transactions list are displayed in descending order.",
		groups = {"Functional"},
		dependsOnMethods = {"Verify_RecentTransactions_ListSize"},
		priority = 5
	)
	public void Verify_RecentTransactionsDates_inDescOrder() {
		WebDriver driver = WebDriverContext.getDriver();
		
		QAplaygroundLogin login = new QAplaygroundLogin(driver);
		login.open_QAplayground_Url();
		login.Login_QAplaygroundBank(LoginData.activeUser, LoginData.password);
		
		Dashboard dash = new Dashboard(driver);
		List<LocalDate> actualorder = dash.getRcntTrancDates();
		List<LocalDate> expectedorder = dash.descSort_RcntTrancDates();
		
		Assert.assertEquals(actualorder, expectedorder, "Dates are not in descending order.");
		
		System.out.println("Recent Transactions dates : " + actualorder);	
	}
	
	@Test(
	   testName = "Verify URL After Navigating to Transfer Page",
	   description = "Verify that the application navigates to the Transfer page and displays the correct URL.",
	   groups = {"Functional", "Navigation"},
	   priority = 6
	)
	public void Verify_url_afterNavaigatingTo_Transfer() {
		WebDriver driver = WebDriverContext.getDriver();

	    QAplaygroundLogin login = new QAplaygroundLogin(driver);
	    Dashboard dash = new Dashboard(driver);

	    login.open_QAplayground_Url();
	    login.Login_QAplaygroundBank(LoginData.activeUser, LoginData.password);
	    
	    String expectedUrl = "https://qaplayground.com/bank/transfer";
	    
	    dash.clickTransferMoney();
		dash.waitForurl(expectedUrl);
		
	    String currentUrl = driver.getCurrentUrl();

		Assert.assertEquals(currentUrl, expectedUrl, "Dashboard URL is incorrect.");
		
		System.out.println("Current url after navigating : " + currentUrl);			
	}
}