package utils;

import org.testng.annotations.DataProvider;

public class LoginDataProvider {
	@DataProvider(name = "loginData")
	public Object[][] loginData(){
		
		return new Object[][] {
			{"standard_user", "bank_sauce", true},
			{"practice", "wrong123", false},
            {"wronguser", "SuperSecretPassword!", false},
            {"wronguser", "wrong123", false}			
		};
	}
}
