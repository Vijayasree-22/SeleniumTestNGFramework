package pages;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


import base.BasePage;

public class Transfer extends BasePage{

	public Transfer(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	private By TransferBtn = By.cssSelector("[data-testid='sidebar-link-transfer']");
	private By Transfer_frmAcc = By.cssSelector("[data-testid='transfer-from-select']");
	private By Transfer_toAcc = By.cssSelector("[data-testid='transfer-to-select']");
	private By Transfer_amount = By.cssSelector("[data-testid='transfer-amount-input']");
	private By Transfer_memo = By.cssSelector("[name='transfer_memo_field']");
	private By TransferDate_Today = By.cssSelector("label[data-testid='date-type-today']");
	private By TransferDate_Schedule = By.cssSelector("label[data-testid='date-type-scheduled']");
    private By Transfer_frmOptions = By.cssSelector("div[data-testid='transfer-from-option']");
    private By Transfer_toOptions = By.cssSelector("div[data-testid='transfer-to-option']");
    private By checkingOption = By.cssSelector("div[data-account-id='acc-checking-1']");
    private By savingsOption = By.cssSelector("div[data-account-id='acc-savings-1']");
    private By availableBlncTotransfer = By.cssSelector("span[data-testid='transfer-available-balance']");
    private By reviewTransferBtn = By.cssSelector("[data-testid='review-transfer-btn']");
    private By scheduleDateInp = By.cssSelector("[data-testid='transfer-scheduled-date-input']");
    private By confirmTransfer_confirmBtn = By.cssSelector("[data-testid='confirm-transfer-btn']");
    private By TransferError_insufficientfunds = By.cssSelector("[data-testid='transfer-error-message']");
    private By TransfersuccessSection = By.cssSelector("[data-testid='transfer-confirmation-page']");
    
	public void clickTransferBtn() {
		click(TransferBtn);
	}
	
	public boolean isTransferFieldsDisplayed() {
		return driver.findElement(Transfer_frmAcc).isDisplayed() &&
		driver.findElement(Transfer_toAcc).isDisplayed() &&
		driver.findElement(Transfer_amount).isDisplayed() &&
		driver.findElement(Transfer_memo).isDisplayed();
	}
	
	public void click_Transfer_frmAcc() {
		click(Transfer_frmAcc);
	}	
	
	public void click_Transfer_toAcc() {
		click(Transfer_toAcc);
	}
	
	public List<String> getTransferFrom_options() {
		click_Transfer_frmAcc(); 
		try { Thread.sleep(500); } catch (Exception e) {} 
		
		List<WebElement> frm_opts = driver.findElements(Transfer_frmOptions);
		List<String> optionsNames = new ArrayList<>();
		for(WebElement opt : frm_opts) {
			optionsNames.add(opt.getAttribute("data-account-id"));
		}
		return optionsNames; // No need to close it!
	}
	
	public void select_TransferFrom_option(String account) {
		click_Transfer_frmAcc(); 
		try { Thread.sleep(500); } catch (Exception e) {} 
		
		WebElement optToSelect = driver.findElement(By.cssSelector("div[data-testid='transfer-from-option'][data-account-id='" +account+ "']"));
		optToSelect.click();
	}
	
	public List<String> getTransferTo_options() {
		click_Transfer_toAcc(); 
		try { Thread.sleep(500); } catch (Exception e) {} 
		
		List<WebElement> to_opts = driver.findElements(Transfer_toOptions);
		List<String> toOptions = new ArrayList<>();
		for(WebElement opt : to_opts) {
			toOptions.add(opt.getAttribute("data-account-id"));
		}
		return toOptions; // No need to close it!
	}
	
	public void select_TransferTo_option(String account) {
		click_Transfer_toAcc(); 
		try { Thread.sleep(500); } catch (Exception e) {} 
		
		WebElement optToSelect = driver.findElement(By.cssSelector("div[data-testid='transfer-to-option'][data-account-id='" +account+ "']"));
		optToSelect.click(); 
	}
	
	public double getAvBalnc() {
		String text = getText(availableBlncTotransfer).replace("$", "");
		       text = text.replace(",", "");
		 double balance = Double.parseDouble(text);
		 return balance;
	}
	
	public void transfer_Enteramount(double amount) {
		driver.findElement(Transfer_amount).sendKeys(String.valueOf(amount));
	}
	
	public void EnterMemo(String memo) {
		sendKeys(Transfer_memo, memo);
	}
	
	public void selectDate(String date) {
		boolean TodayisSelected = driver.findElement(TransferDate_Today).isSelected();
		
		if(date.equals("Today")) {
			if(!TodayisSelected){
				click(TransferDate_Today);
			}
		}else if(date.equals("scheduled")) {
			click(TransferDate_Schedule);
			LocalDate currentDate = LocalDate.now();
	        
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); 
	        
	        String formattedDate = currentDate.format(formatter);
	        
	        sendKeys(scheduleDateInp, formattedDate);
		}			
		}
	
	public void clickreviewTransferBtn() {
	        click(reviewTransferBtn);
	}
	
	public void clickConfirmTransfer() {
		   click(confirmTransfer_confirmBtn);
	}
	
	public String getTransferError() {
		   return getText(TransferError_insufficientfunds);
	}
	
	public boolean isTransferError_displayed() {
		   return driver.findElement(TransferError_insufficientfunds).isDisplayed();
	}
	
	public String getTransfersuccessDetails() {
		return getText(TransfersuccessSection);
	}
	
	public boolean isTransfersuccessDisplayed() {
		return driver.findElement(TransfersuccessSection).isDisplayed();
	}
	
	public void enterPastdate_InSchedule() {
		click(TransferDate_Schedule);
		LocalDate Date = LocalDate.now().minusDays(1);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/mm/yyyy");
		String yesterdayDate = Date.format(formatter);
		sendKeys(scheduleDateInp, yesterdayDate);
	}

}
