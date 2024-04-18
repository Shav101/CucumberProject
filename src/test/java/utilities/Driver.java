package utilities;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;



public class Driver {
	
	/* Driver class is reusable class for webDriver and it checks the webDriver on the system.
	 * If there isn't any driver on the system, it downloads the driver and sets up the path and environment  
	 * For this purpose, I've used WebDriver manager
	 * And if I want to run my script on different browser, 
	 * all I have to do is change the browser name in the properties file.
	 */
	public static WebDriver driver;
	static ChromeOptions chromeOptions;

		public static WebDriver getDriver() {
			String browserType = System.getProperty("browser");
			if (browserType == null) {
				browserType = DataReader.getProperty("browser_type");
			}
			if (driver == null || ((RemoteWebDriver) driver).getSessionId() == null) {
				switch(browserType) {
				case "chrome-headless" :
				    chromeOptions = new ChromeOptions();
					chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
					chromeOptions.addArguments("--headless");
					driver = new ChromeDriver(chromeOptions);
					break;
				case "chrome" :
					driver = new ChromeDriver();
					break;
				case "firefox" :
					driver = new FirefoxDriver();
					break;
				case "firefox-headless" :
					FirefoxOptions firefoxOptions = new FirefoxOptions();
					firefoxOptions.addArguments("--headless");
					driver = new FirefoxDriver(firefoxOptions);
					break;
				case "safari" :
					driver = new SafariDriver();
					break;
				case "edge" :
					driver = new EdgeDriver();
					break;
				case "saucelabs" :
					EdgeOptions browserOptions = new EdgeOptions();
					browserOptions.setPlatformName("Windows 11");
					browserOptions.setBrowserVersion("latest");
					Map<String, Object> sauceOptions = new HashMap<>();
					sauceOptions.put("username", "Your username");
					sauceOptions.put("accessKey", "Your secret key");
					sauceOptions.put("build", "10012");
					sauceOptions.put("name", "windows11_edge_latest");
					browserOptions.setCapability("sauce:options", sauceOptions);

					URL url;
					try {
						url = new URL("Your saucelab HUB url");
						driver = new RemoteWebDriver(url, browserOptions);
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				default:
					ChromeOptions Options = new ChromeOptions();
					Options.addArguments("--headless");
					driver = new ChromeDriver(Options);
					break;
				}
			}
		return driver;
	}
	
//	public static WebDriver driver;
	
//	public static WebDriver getDriver() {
//		
//		System.setProperty("webdriver.chrome.driver", "E:\\SeleniumTools\\chromeDriver\\chromedriver.exe");
//		
//		// so it does not create multiple objects
//		// bc when u instantiate once it is not going to be null
//		if (driver == null) {
//			driver = new ChromeDriver(); 
//			
//		}
//		
////		driver = new ChromeDriver();
//
//		return driver;
//		
//	}
	
	public static void quitDriver() {
		
		if (driver != null) {
			
			driver.quit();
			driver = null;
		}
		
	}
}
	
	
