package tests;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import context.WebDriverContext;
import pages.Account;
import pages.QAplaygroundLogin;
import pages.Transfer;
import utils.LoginData;
import utils.TD;

public class TransferTest extends BaseTest {
	
	@Test(
		testName = "Verify Internal Transfer Form Loads",
		description = "Checks if the transfer fields are displayed upon navigating to the Transfer page.",
		groups = {"Functional", "Smoke"},
		priority = 1
	)
	public void Verify_InternalTransferForm_Load() {
		WebDriver driver = WebDriverContext.getDriver();
		QAplaygroundLogin login = new QAplaygroundLogin(driver);
		Transfer transfer = new Transfer(driver);

		login.open_QAplayground_Url();
		login.Login_QAplaygroundBank(LoginData.activeUser, LoginData.password);
		transfer.clickTransferBtn();
		Assert.assertTrue(transfer.isTransferFieldsDisplayed(), "Transfer details are not present");
	}
	
	@Test(
		testName = "Verify 'From' and 'To' Account Dropdown Logic",
		description = "Ensures that the account selected in the 'From' dropdown is removed from the 'To' dropdown options.",
		groups = {"Functional", "UI"},
		dependsOnMethods = {"Verify_InternalTransferForm_Load"},
		priority = 2
	)
	public void Verify_FromToaccounts_Tranferform() {
		WebDriver driver = WebDriverContext.getDriver();
		QAplaygroundLogin login = new QAplaygroundLogin(driver);
		Transfer transfer = new Transfer(driver);

		login.open_QAplayground_Url();
		login.Login_QAplaygroundBank(LoginData.activeUser, LoginData.password);
		transfer.clickTransferBtn();
		
		// 1. Get options once before the loop starts
		List<String> fromaccount = transfer.getTransferFrom_options();
		driver.navigate().refresh(); // Refresh to reset the UI after reading
		
		for(String accId : fromaccount) {
			// If refresh takes you away from the transfer page, uncomment the next line:
			// transfer.clickTransferBtn(); 
			
			transfer.select_TransferFrom_option(accId);
			List<String> toOptions = transfer.getTransferTo_options();
			
			System.out.println("Dpd1 option selected :" + accId);
			System.out.println("Dpd2 options generated :" + toOptions);
			System.out.println("-----------------------------");
			
			Assert.assertFalse(toOptions.contains(accId), "Bug found! Account ID " + accId + " is showing in Dropdown 2");
			
			// YOUR GENIUS MOVE: Hard reset the UI for the next loop!
			driver.navigate().refresh(); 
		}
	}
	
	@Test(
		testName = "Verify Insufficient Funds Validation",
		description = "Attempts a transfer exceeding the available balance and validates the correct error message is shown.",
		groups = {"Functional", "Negative"},
		priority = 3
	)
	public void Verify_InsufficientFundsValidation() {
		WebDriver driver = WebDriverContext.getDriver();
		QAplaygroundLogin login = new QAplaygroundLogin(driver);
		Transfer transfer = new Transfer(driver);

		login.open_QAplayground_Url();
		login.Login_QAplaygroundBank(LoginData.activeUser, LoginData.password);
		transfer.clickTransferBtn();
		
		transfer.select_TransferFrom_option(TD.Transfermoney.savings);
		transfer.select_TransferTo_option(TD.Transfermoney.checking);
		
		double balance = transfer.getAvBalnc();
		transfer.transfer_Enteramount(balance + 1000);
		transfer.EnterMemo("rent");
		transfer.selectDate(TD.Transfermoney.today_date);
		transfer.clickreviewTransferBtn();
		transfer.clickConfirmTransfer();
		
		Assert.assertTrue(transfer.isTransferError_displayed(), "insufficientfundsError is not displayed");
		
		String error = transfer.getTransferError();
		System.out.println("Error displayed after transfering more than available balance ---> " + error);
	}
	
	@Test(
		testName = "Verify Successful Internal Transfer",
		description = "Executes a valid internal transfer and verifies the success confirmation screen.",
		groups = {"Functional", "Smoke"},
		priority = 4
	)
	public void Verify_SuccessfulInternalTransfer() {
		WebDriver driver = WebDriverContext.getDriver();
		QAplaygroundLogin login = new QAplaygroundLogin(driver);
		Transfer transfer = new Transfer(driver);

		login.open_QAplayground_Url();
		login.Login_QAplaygroundBank(LoginData.activeUser, LoginData.password);
		transfer.clickTransferBtn();
		
		transfer.select_TransferFrom_option(TD.Transfermoney.checking);
		transfer.select_TransferTo_option(TD.Transfermoney.savings);
		
		double balance = transfer.getAvBalnc();
		if(balance < 1) {
			Assert.fail("Insufficient balance! Cannot proceed with transfer. Current balance is: " + balance);
		}
		
		transfer.transfer_Enteramount(0.1);
		transfer.EnterMemo("holiday");
		transfer.selectDate(TD.Transfermoney.today_date);
		transfer.clickreviewTransferBtn();
		transfer.clickConfirmTransfer();
		
		Assert.assertTrue(transfer.isTransfersuccessDisplayed());
		
		String details = transfer.getTransfersuccessDetails();
		System.out.println(details);
	}
	
	@Test(
		testName = "Verify Scheduled Transfer with Past Date",
		description = "Inputs a past date in the schedule field and attempts to proceed to review.",
		groups = {"Functional", "Negative"},
		priority = 5
	)
	public void Verify_ScheduledTransferFlow() {
		WebDriver driver = WebDriverContext.getDriver();
		QAplaygroundLogin login = new QAplaygroundLogin(driver);
		Transfer transfer = new Transfer(driver);

		login.open_QAplayground_Url();
		login.Login_QAplaygroundBank(LoginData.activeUser, LoginData.password);
		transfer.clickTransferBtn();
		
		transfer.select_TransferFrom_option(TD.Transfermoney.savings);
		transfer.select_TransferTo_option(TD.Transfermoney.checking);
		
		double balance = transfer.getAvBalnc();
		if(balance < 1) {
			Assert.fail("Insufficient balance! Cannot proceed with transfer. Current balance is: " + balance);
		}
		
		transfer.transfer_Enteramount(0.1);
		transfer.EnterMemo("holiday");
		transfer.enterPastdate_InSchedule();
		transfer.clickreviewTransferBtn();
	}
	
	@Test(
		testName = "Verify Balance Updates Post-Transfer",
		description = "Validates that checking and savings balances correctly reflect the deducted and added amounts after a transfer.",
		groups = {"Functional", "Integration"},
		dependsOnMethods = {"Verify_SuccessfulInternalTransfer"},
		priority = 6
	)
	public void Verify_TransferBalancesUpdate() {
		WebDriver driver = WebDriverContext.getDriver();
		QAplaygroundLogin login = new QAplaygroundLogin(driver);
		Transfer transfer = new Transfer(driver);
		Account acc = new Account(driver);
		
		login.open_QAplayground_Url();
		login.Login_QAplaygroundBank(LoginData.activeUser, LoginData.password);
		acc.navigateToAccounts();
		
		double checkingBalancebeforeTransfer = acc.getCheckingAccBalance();
		double savingsBalancebeforeTransfer = acc.getSavingsAccBalance();
		
		transfer.clickTransferBtn();
		
		transfer.select_TransferFrom_option(TD.Transfermoney.checking);
		transfer.select_TransferTo_option(TD.Transfermoney.savings);
		
		double balance = transfer.getAvBalnc();
		if(balance < 1) {
			Assert.fail("Insufficient balance! Cannot proceed with transfer. Current balance is: " + balance);
		}
		
		double amount = 10.0;
		transfer.transfer_Enteramount(amount);
		transfer.EnterMemo("holiday");
		transfer.selectDate(TD.Transfermoney.today_date);
		transfer.clickreviewTransferBtn();
		transfer.clickConfirmTransfer();
		
		Assert.assertTrue(transfer.isTransfersuccessDisplayed());
		
		double expectedcheckingBalance = (checkingBalancebeforeTransfer - amount);
		double expectedsavingsBalance = (savingsBalancebeforeTransfer + amount);

		acc.navigateToAccounts();
		
		double checkingBalanceafterTransfer = acc.getCheckingAccBalance();
		double savingsBalanceafterTransfer = acc.getSavingsAccBalance();
		
		System.out.println("amount transferred from checking acc to savings acc : " + amount);
		System.out.println("checking Balance before Transfer : " + checkingBalancebeforeTransfer);
		System.out.println("checking Balance after Transfer : " + checkingBalanceafterTransfer);
		System.out.println("savings Balance before Transfer : " + savingsBalancebeforeTransfer);
		System.out.println("savings Balance after Transfer : " + savingsBalanceafterTransfer);

		Assert.assertEquals(checkingBalanceafterTransfer, expectedcheckingBalance, "wrong checking Balance");
		Assert.assertEquals(savingsBalanceafterTransfer, expectedsavingsBalance, "wrong savings Balance");
	}
}