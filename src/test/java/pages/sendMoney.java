package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import base.BasePage;

public class sendMoney extends BasePage{
	public sendMoney(WebDriver driver) {
		super(driver);
	};
	
	private By sendMoneyBtn = By.cssSelector("[data-testid='sidebar-link-send-money']");
	private By addPayeeBtn = By.cssSelector("[data-testid='add-payee-btn']");
	private By PayeeDpd = By.id("payee-select-trigger");
	private By reviewSendBtn = By.cssSelector("[data-testid='review-send-btn']");

	//AddNewPayee
	private By payeeName = By.id("add-payee-name");
	private By payeeBank = By.id("add-payee-bank");
	private By routingNumber = By.cssSelector("[data-testid='add-payee-routing-input']");
	private By accNumber = By.id("add-payee-account");
	private By submit_addPayee = By.cssSelector("[data-testid='save-add-payee-btn']");
	private By payeeDynamicOptions = By.cssSelector("[data-testid='payee-select-option']");


	public void NavigateTo_sendMoney() {
		click(sendMoneyBtn);
	}
	
	public void openAddNewPayee_popup() {
		click(addPayeeBtn);
	}
	
	public void Add_NewPayee(String name, String bank, int rNum, long acNum) throws InterruptedException {
		openAddNewPayee_popup();
		
	    sendKeys(payeeName, name);
		sendKeys(payeeBank, bank);
		sendKeys(routingNumber, String.valueOf(acNum));
		sendKeys(accNumber, String.valueOf(acNum));	
	}
	
	public void click_submit_addPayee() {
		click(submit_addPayee);
	}
	
	public void clickPayee() {
		click(PayeeDpd);
	}
	
	public boolean isnewlyaddedPayeeOptionListed(String name, String bank) {
		clickPayee();
		List<WebElement> payeeOptions = driver.findElements(payeeDynamicOptions);
		boolean found = false;
		for(WebElement opt : payeeOptions) {
			String text = opt.getText();
			if(text.contains(bank) && text.contains(name)) {
				found = true;
				System.out.println("Found Option: " + text);
				break;
			}
		}
		return found;
	}
	
	public boolean isReviewTransferEnabled() {
		return driver.findElement(reviewSendBtn).isEnabled();
	}
	
	
	
	
	
}
	
	

	