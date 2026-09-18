package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import base.BasePage;

public class Account extends BasePage{
	
	public Account(WebDriver driver) {
		super(driver);
	}
	private By AccountTab = By.cssSelector("[data-testid='sidebar-link-accounts']");

	//Account
	private By AccountspageH1 = By.cssSelector("[data-testid='accounts-page-title']");
	private By accountsTable = By.cssSelector("table[data-testid='accounts-table']");
	private By accountRows = By.cssSelector("table[data-testid='accounts-table'] tr[data-testid='account-row']");
	private By accBalance = By.cssSelector("table[data-testid='accounts-table'] tr[data-testid='account-row'] td[data-balance]");
	private By checkingAcc = By.cssSelector("tr[data-account-type='checking']");
	private By savingsAcc = By.cssSelector("tr[data-account-type='savings']");
	private By CheckingAcc_viewBtn = By.cssSelector("tr[data-account-type='checking'] [data-testid='view-account-btn']");
	private By SavingsAcc_viewBtn = By.cssSelector("tr[data-account-type='savings'] [data-testid='view-account-btn']");
	private By CheckingAcc_status = By.xpath("//tr[@data-account-type='checking']//span[@data-testid=\"account-row-overdrawn\"]");
	private By checkingaccBalance = By.cssSelector("tr[data-account-id='acc-checking-1'] td[data-testid='account-row-balance']");
	private By savingsaccBalance = By.cssSelector("tr[data-account-id='acc-savings-1'] td[data-testid='account-row-balance']");

	//Add Account
	private By addAccountBtn = By.xpath("//button[@data-testid='add-account-btn']");
	private By Add_AccName_Inpt = By.cssSelector("[data-testid='account-form-name-input']");
	private By Add_AccType_dpd = By.id("account-form-type-trigger");
	private By Add_AccBlnc_Inpt = By.xpath("//input[@name='account_balance_field']");
	private By Addacc_submitBtn = By.cssSelector("[data-testid=\"save-account-form-btn\"]");
	private By Addacc_Termscheckbox = By.cssSelector("[data-testid='account-form-accept-terms-checkbox']");
	//View Account
	private By AccName = By.xpath("//h1[@data-testid='account-detail-name']");
	private By AccBadge = By.cssSelector("[data-testid='account-detail-type-badge']");
	private By AccNumber = By.cssSelector("p.mt-1.font-mono.text-sm.text-slate-500");
	
	public void navigateToAccounts() {
		click(AccountTab);
	}
	
	public String getAccountsHeading() {
		waitForVisibility(AccountspageH1);
		return driver.findElement(AccountspageH1).getText();		
	}
	
	public boolean isAccountsH1Displayed() {
		return getAccountsHeading().contains("My Accounts");
	}
	
    public double getAccsBalance() {
    	
    	double sumOfAccsBalance = 0;
    	
    	wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(accountRows));
    	//wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(accountRows, 0));
		List<WebElement> accountRows_locator = driver.findElements(this.accountRows);
		
		
		for(WebElement accrw : accountRows_locator) {
		WebElement BalanceOfeachAcc = accrw.findElement(accBalance);
		sumOfAccsBalance += Double.parseDouble(BalanceOfeachAcc.getAttribute("data-balance"));
		System.out.println(sumOfAccsBalance);
		}
		return sumOfAccsBalance;
	    }
    
     public boolean ischeckingAccDisplayed() {
    	 boolean checkingAccLocator = driver.findElement(checkingAcc).isDisplayed();
		 return checkingAccLocator;  	 
        }
    
     public boolean issavingsAccDisplayed() {
        	boolean savingAccLocator = driver.findElement(savingsAcc).isDisplayed();
			return savingAccLocator;
        }
     
     public String status_checkingAcc() {
    	 return getText(CheckingAcc_status);
     }
        
     public void view_checkingAcc() {
    	 click(CheckingAcc_viewBtn);
     }
     
     public void view_savingsAcc() {
    	 click(SavingsAcc_viewBtn);
     }
     
     //Add Account
     public void OpenAddAcc_pop() {
    	 click(addAccountBtn);
     }
     
     public By addAcc_getAccountoptions(String type) {
    	 return By.xpath("//div[@data-account-type='"+ type.toLowerCase() +"']");
     }
     public void addAccountDetails(String accName, String type, double balance ) throws InterruptedException {
    	 sendKeys(Add_AccName_Inpt, accName);
    	 click(Add_AccType_dpd);
    	 click(addAcc_getAccountoptions(type));
    	 click(Add_AccBlnc_Inpt);
    	 driver.findElement(Add_AccBlnc_Inpt).sendKeys(String.valueOf(balance)); 
    	 driver.findElement(Addacc_Termscheckbox).click();
    	 click(Addacc_submitBtn);
     }
     
     //View Account
     public String get_AccName_ViewAcc() {
    	 return getText(AccName);
     }
     
     public String get_AccBadge_ViewAcc() {
    	 return getText(AccBadge);
     }
     
     public String get_AccNumber_ViewAcc() {
		return getText(AccNumber);
    	 
     }
     
     public boolean isAccNumberDisplayed_viewAcc() {
    	return driver.findElement(AccNumber).isDisplayed();
     }
     
     public boolean isAccNameDisplayed_viewAcc() {
     	return driver.findElement(AccName).isDisplayed();
      }
     
     public boolean isAccBadgeDisplayed_viewAcc() {
     	return driver.findElement(AccBadge).isDisplayed();
      }     
     
     public double getCheckingAccBalance() {
    	String text = getText(checkingaccBalance);
    	text = text.replace("$", "");
    	text = text.replace(",", "");
    	double balance = Double.parseDouble(text);
    	return balance;
     }
     
     public double getSavingsAccBalance() {
     	String text = getText(savingsaccBalance);
     	text = text.replace("$", "");
     	text = text.replace(",", "");
     	double balance = Double.parseDouble(text);
     	return balance;
      }
     
}
