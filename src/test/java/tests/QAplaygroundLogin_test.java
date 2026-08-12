package tests;

import org.openqa.selenium.WebDriver;

import utils.LoginData;
import utils.LoginDataProvider;

import org.testng.Assert;
import org.testng.annotations.Test;
import context.WebDriverContext;
import pages.QAplaygroundLogin;

public class QAplaygroundLogin_test extends BaseTest {

	@Test(
	        testName = "Valid Login Test",
	        description = "Verifies that a user can successfully log in using valid active credentials.",
	        priority = 0,
	        groups = {"Functional", "Smoke"},
	        alwaysRun = true
	    )
    public void testQAplaygroundLogin() {

        WebDriver driver = WebDriverContext.getDriver();

        QAplaygroundLogin login =
                new QAplaygroundLogin(driver);

        login.open_QAplayground_Url();

        login.Login_QAplaygroundBank(LoginData.activeUser, LoginData.password);
        
        
    }
    
	@Test(
	        testName = "Data-Driven Negative Login",
	        description = "Tests the login functionality against a dataset of invalid credentials and verifies error messages.",
	        dataProvider = "loginData",
	        dataProviderClass = LoginDataProvider.class,
	        priority = 1,
	        groups = {"Negative"}
	    )
    	public void Loginfunctionality(String username,
    	                               String password,
    	                               boolean expected) {

    	    WebDriver driver = WebDriverContext.getDriver();

    	    QAplaygroundLogin login = new QAplaygroundLogin(driver);

    	    login.open_QAplayground_Url();

    	    login.Login_QAplaygroundBank(username, password);

    	    if(expected) {
    	        Assert.assertEquals(login.getSecurebankTitle(),"SecureBank");
    	        login.logout();
    	    }else {
    	        Assert.assertEquals(login.getLoginError(),
    	                "The username or password you entered is incorrect.");
    	    }
    	}
    }
