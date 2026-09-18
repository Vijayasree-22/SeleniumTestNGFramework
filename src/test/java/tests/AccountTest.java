package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import context.WebDriverContext;
import pages.Account;
import pages.Dashboard;
import pages.QAplaygroundLogin;
import utils.LoginData;
import utils.TD;

public class AccountTest extends BaseTest {
	
	@Test(
		testName = "Verify Accounts List Load",
		description = "Verifies that both the checking and savings accounts are displayed on the Accounts page.",
		groups = {"Functional", "Smoke"},
		priority = 1
	)
	public void VerifyAccountsListLoad() {
		WebDriver driver = WebDriverContext.getDriver();
		QAplaygroundLogin login = new QAplaygroundLogin(driver);
		Account acc = new Account(driver);

		login.open_QAplayground_Url();
		login.Login_QAplaygroundBank(LoginData.activeUser, LoginData.password);
		acc.navigateToAccounts();
		
		Assert.assertTrue(acc.ischeckingAccDisplayed());
		Assert.assertTrue(acc.issavingsAccDisplayed());
	}
	
	@Test(
		testName = "Verify Account Details Display",
		description = "Checks that account name, badge, and correctly masked account number are visible in Account Details.",
		groups = {"Functional", "UI"},
		dependsOnMethods = {"VerifyAccountsListLoad"},
		priority = 2
	)
	public void Verify_AccountDetails_Display() {
		WebDriver driver = WebDriverContext.getDriver();
		QAplaygroundLogin login = new QAplaygroundLogin(driver);
		Account acc = new Account(driver);

		login.open_QAplayground_Url();
		login.Login_QAplaygroundBank(LoginData.activeUser, LoginData.password);
		acc.navigateToAccounts();
		
		acc.view_checkingAcc();
		SoftAssert softassert = new SoftAssert();
		softassert.assertTrue(acc.isAccNameDisplayed_viewAcc(), "Account Name is not displayed in Account Details");
		softassert.assertTrue(acc.isAccBadgeDisplayed_viewAcc(), "Account Badge is not displayed in Account Details");
		softassert.assertTrue(acc.isAccNumberDisplayed_viewAcc(), "Account Number is not displayed in Account Details");
		
		String accountNumber = acc.get_AccNumber_ViewAcc();
		System.out.println(accountNumber);

		Assert.assertTrue(
				accountNumber.matches("\\*{4}\\d{4}"),
				"Account number is not masked correctly.");

		System.out.println("Account Name :" + acc.get_AccName_ViewAcc());
		System.out.println("Account Badge :" + acc.get_AccBadge_ViewAcc());
		System.out.println("Account Number :" + acc.get_AccNumber_ViewAcc());
		
		softassert.assertAll();
	}

	@Test(
		testName = "Verify Overdraft Banner",
		description = "Logs in with an overdrawn account user and verifies the account status shows as 'Overdrawn'.",
		groups = {"Functional", "EdgeCase"},
		priority = 3
	)
	public void Verify_OverdraftBanner() {
		WebDriver driver = WebDriverContext.getDriver();
		QAplaygroundLogin login = new QAplaygroundLogin(driver);
		Account acc = new Account(driver);

		login.open_QAplayground_Url();
		login.Login_QAplaygroundBank(LoginData.overdraft_username, LoginData.password);
		acc.navigateToAccounts();
		
		String status = acc.status_checkingAcc();
		
		Assert.assertEquals(status, "Overdrawn", "checking account status is not Overdrawn");
		
		System.out.println("CheckingAcc status :" + status);
	}

	@Test(
		testName = "Add Account Details",
		description = "Opens the Add Account modal, fills out the details using test data, and submits the form.",
		groups = {"Functional", "DataEntry"},
		dependsOnMethods = {"VerifyAccountsListLoad"},
		priority = 4
	)
	public void AddAccountDetails() throws InterruptedException {
		WebDriver driver = WebDriverContext.getDriver();
		QAplaygroundLogin login = new QAplaygroundLogin(driver);
		Account acc = new Account(driver);

		login.open_QAplayground_Url();
		login.Login_QAplaygroundBank(LoginData.activeUser, LoginData.password);
		acc.navigateToAccounts();
		
		acc.OpenAddAcc_pop();
		acc.addAccountDetails(TD.addAccount.accName, TD.addAccount.creditType, TD.addAccount.accBalance);
	}
}