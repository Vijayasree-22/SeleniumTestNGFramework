package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import context.WebDriverContext;
import pages.QAplaygroundLogin;
import pages.Transfer;
import pages.sendMoney;
import utils.LoginData;
import utils.TD;

public class sendMoneyTest extends BaseTest {
	
	@Test(
		testName = "Add and Verify New Payee",
		description = "Adds a new payee using test data and verifies that the newly added payee is listed in the dropdown options.",
		groups = {"Functional", "Smoke"},
		priority = 1
	)
	public void addNewPayee_Verify_inPayeeOptions() throws InterruptedException {
		WebDriver driver = WebDriverContext.getDriver();
		QAplaygroundLogin login = new QAplaygroundLogin(driver);
		sendMoney sm = new sendMoney(driver);

		login.open_QAplayground_Url();
		login.Login_QAplaygroundBank(LoginData.activeUser, LoginData.password);
		sm.NavigateTo_sendMoney();
		
		try {
			sm.Add_NewPayee(TD.AddPayee.name, TD.AddPayee.bank, TD.AddPayee.RoutingNum, TD.AddPayee.accNum);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sm.click_submit_addPayee();
		Assert.assertTrue(sm.isnewlyaddedPayeeOptionListed(TD.AddPayee.name, TD.AddPayee.bank),
				"Newly added option is not listed in Payee");
	}
	
	@Test(
		testName = "Verify Frozen Account Block",
		description = "Logs in with a frozen account user and validates that the Review Transfer button is disabled.",
		groups = {"Functional", "EdgeCase"},
		priority = 2
	)
	public void Verify_FrozenAccountBlock() {
		WebDriver driver = WebDriverContext.getDriver();
		QAplaygroundLogin login = new QAplaygroundLogin(driver);
		sendMoney sm = new sendMoney(driver);

		login.open_QAplayground_Url();
		login.Login_QAplaygroundBank(LoginData.frozenacc_username, LoginData.password);
		sm.NavigateTo_sendMoney();
		
		Assert.assertFalse(sm.isReviewTransferEnabled(), "Review transfer is enabled for frozen account");
	}
}