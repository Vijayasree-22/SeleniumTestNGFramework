package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import base.BasePage;

public class BillPay extends BasePage {

	public BillPay(WebDriver driver) {
		super(driver);
	}
	
	private By BillpayBtn = By.cssSelector("[data-testid='sidebar-link-bill-pay']");
	private By Biller = By.id("biller-search-input");

	
	public void NavigateToBillpay() {
		click(BillpayBtn);
	}
}
