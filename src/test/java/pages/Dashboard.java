package pages;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import base.BasePage;


public class Dashboard extends BasePage{
	
	public Dashboard(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	private By dashboardWelcomeMsg = By.cssSelector("[data-testid='dashboard-welcome-message']");
	private By dashboardstatcards = By.cssSelector("[data-testid='dashboard-stat-cards']");
	private By TotalnetworthValue = By.cssSelector("[data-testid='stat-card-net-worth-value']");
	private By recentTrnscTable = By.cssSelector("[data-testid='recent-transactions-table']");
	private By recentTrnscRows = By.cssSelector("table[data-testid='recent-transactions-table'] tr[data-testid='recent-txn-row']");
	private By recentTrnscDate = By.cssSelector("tr[data-testid='recent-txn-row'] td[data-testid='recent-txn-date'] time[datetime]");
	private By transfermoneyCard = By.xpath("//a[@data-testid='quick-action-transfer']");
	private By themeBtn = By.className("nav-module__Efbrta__themeToggle");
	
	public String getdashboardWelcomeMsg() {
		waitForVisibility(dashboardWelcomeMsg);
		return getText(dashboardWelcomeMsg);
	}
	
	public boolean isWelcomeMessageVisible() {
		return getdashboardWelcomeMsg().contains("Welcome back, ");
	}
	
	public String getTotalnetworthValue() {
		waitForVisibility(dashboardstatcards);
		return getText(TotalnetworthValue);
	}
	public Double convertTotalnetworthValue() {
		String total = getTotalnetworthValue().replace("$", "").replace(",", "");
		double Totalnet = Double.parseDouble(total);
		return Totalnet;
	}
	public int recentTrnscCount() {
		
		int count = 0;
		List<WebElement> rows = driver.findElements(recentTrnscRows);
		count = rows.size();		
		return count;
	}
	
	public List<LocalDate> getRcntTrancDates() {
		
		List<WebElement> recentTrancDateLocator = driver.findElements(recentTrnscDate);
		List<LocalDate> actualDates = new ArrayList<>();
		
		for(WebElement date : recentTrancDateLocator) {
			actualDates.add(LocalDate.parse(date.getAttribute("datetime")));
			}
		return actualDates;
		
	}
	
	public List<LocalDate> descSort_RcntTrancDates() {
		
        List<LocalDate> descSortedDates = new ArrayList<>(getRcntTrancDates());
		descSortedDates.sort(Comparator.reverseOrder());
		return descSortedDates;
	}
	
	public void clickTransferMoney() {		
		click(transfermoneyCard);
	}
	
	public void waitForurl(String expectedUrl) {
		waitForurlToBe(expectedUrl);
	}
	
	public void clickThemetoggle() {
		click(themeBtn);
	}

	public String getThemeState() {
	    return driver.findElement(themeBtn).getAttribute("aria-label");
	}
	
	public void waitForThemeToChange(String currentState) {
	    wait.until(ExpectedConditions.not(
	        ExpectedConditions.attributeToBe(themeBtn, "aria-label", currentState)));
	}
	
	
	
}
