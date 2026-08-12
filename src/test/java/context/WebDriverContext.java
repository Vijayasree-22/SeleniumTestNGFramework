package context;
import org.openqa.selenium.WebDriver;

public class WebDriverContext {
	
	private static ThreadLocal<WebDriver> driverInstance = new ThreadLocal<>();
	
	public static WebDriver getDriver() {
		if(driverInstance.get() == null) {
			throw new IllegalStateException("WebDriver has not been set, Please set WebDriver instance by WebDriverContext.setDriver...");
		}else {
			return driverInstance.get();
		}
		}
	public static void setDriver(WebDriver driver) {
		driverInstance.set(driver);
	}
	
	public static void teardriver() {
		if(driverInstance.get() != null) {
			driverInstance.get().quit();
			driverInstance.remove();
		}
	}
}
