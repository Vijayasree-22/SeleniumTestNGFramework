package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import utils.LoginData;

import base.BasePage;

public class QAplaygroundLogin extends BasePage {

    private By usernameInput = By.id("login-username");
    private By passwordInput = By.id("login-password");
    private By submitLogin = By.cssSelector("[data-testid='login-submit-btn']");
    private By securebankTitle = By.xpath("//span[text()='SecureBank']");
    private By LoginError = By.cssSelector("[data-testid='login-error-message']");
    private By logoutBtn = By.cssSelector("[data-testid='topbar-logout-btn']");

    public QAplaygroundLogin(WebDriver driver) {
        super(driver);
    }

    public void open_QAplayground_Url() {
        driver.get(LoginData.url);
    }

    public void Login_QAplaygroundBank(String username, String password) {

        sendKeys(usernameInput, username);
        sendKeys(passwordInput, password);
        click(submitLogin);
    }
    
    public String getSecurebankTitle() {
        return getText(securebankTitle);
    }
    
    public String getLoginError() {
        return getText(LoginError);
    }
    
    public void logout() {
        click(logoutBtn);
    }
    	
    }
