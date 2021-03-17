package core;


import org.openqa.selenium.firefox.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Firefox {
	static Properties p = new Properties();
	static WebDriver driver;

	public static void main(String[] args) throws Exception {
		p.load(new FileInputStream("./input.properties"));
		Logger.getLogger("").setLevel(Level.OFF);
		String driverPath = "";
		if (System.getProperty("os.name").toUpperCase().contains("MAC"))
		driverPath = "/usr/local/bin/geckodriver.sh";
		else if (System.getProperty("os.name").toUpperCase().contains("WINDOWS"))
		driverPath = "c:/windows/msedgedriver.exe";
		else throw new IllegalArgumentException("Unknown OS");
		
		System.setProperty("webdriver.gecko.driver", driverPath);
		System.out.println("Browser: Firefox");
		System.out.println("===================================================");
		driver = new FirefoxDriver();
		driver.get(p.getProperty("url"));
		System.out.println("Page URI: " + driver.getCurrentUrl());
		System.out.println("Page Title: " + driver.getTitle());
		driver.manage().window().maximize();
		WebDriverWait wait = new WebDriverWait(driver, 15);
		
		String file = "/Users/dzmitry/Workspace/input.properties";
		Properties pw = new Properties();
		pw.load(new FileInputStream(file));
		String Amazon_pw = pw.getProperty("amazon_pw");
		
		Actions action = new Actions(driver);
		WebElement we = driver.findElement(By.id(p.getProperty("sign_in_to_hoover")));
		action.moveToElement(we).moveToElement(driver.findElement(By.id(p.getProperty("sign_in_to_hoover")))).click().build().perform();
		
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id(p.getProperty("sign_in_email")))).sendKeys(pw.getProperty("email_value"));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(p.getProperty("sign_in_continue_button")))).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id(p.getProperty("app_pw_field")))).sendKeys(pw.getProperty("amazon_pw"));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id(p.getProperty("sign_in_button")))).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id(p.getProperty("search_input")))).sendKeys(p.getProperty("search_query"));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id(p.getProperty("search_mugnified_glass")))).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(p.getProperty("echo_show_10")))).click();
		String nnn = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(p.getProperty("price_echo_10")))).getText();
		System.out.println("===================================================");
		System.out.println("Echo Show 10 price: " + nnn);
		driver.quit();
}
}
