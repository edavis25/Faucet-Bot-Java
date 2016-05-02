package chromeBot;


import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



public class ChromeBot implements Runnable
{

	ChromeDriver driver;

	// Constructor Parameters as class level variables
	String address;
	String url;
	int numberClaims;


	/*
	 * Constructor - Receives 1)Bitcoin wallet address, 2)referral URL, and 3)number of claims to specify
	 * how the web driver should function. Pass these to class level variables for use.
	 */
	public ChromeBot(String walletAddress, String referralURL, int numberOfClaims)
	{
		address = walletAddress;

		url = referralURL;

		numberClaims = numberOfClaims;
	}


	/*
	 * run() - Necessary method for starting execution when ChromeBot is created.
	 */
	public void run()
	{
		// Find location of the Chrome driver .exe file
		System.setProperty("webdriver.chrome.driver", "./chromedriver_win32/chromedriver.exe");

		// Options for web driver
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--window-size=700,900");

		driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

		// Navigate to given referral URL
		driver.get(url);

		login();

		// Run for number of claims desired. Check for the claim button to click on the webpage,
		// if not found, sleep and look again.
		for (int i = 0; i < numberClaims; i++)
		{
			try
			{
				WebElement claimButton = driver.findElement(By.cssSelector("button"));

				claimButton.click();
			}
			catch (NoSuchElementException e)
			{
				try
				{
					Thread.sleep(3000);
				}
				catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				i--;

				continue;
			}
		}

		driver.quit();

	}

	/*
	 * login() - Navigate through the homepage to input wallet address and submit leading to claim page.
	 */
	private void login()
	{
		// Wait for homepage button animation to finish
		WebDriverWait homeWait = new WebDriverWait(driver, 120);
		homeWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.btn.btn-primary.submit-input.wow.bounceInRight.animated.animated")));


		WebElement bitcoinAddressBox = driver.findElement(By.className("form-control"));
		WebElement submitButton = driver.findElement(By.cssSelector("button.btn.btn-primary.submit-input.wow.bounceInRight.animated.animated"));

		bitcoinAddressBox.sendKeys(address);
		submitButton.click();

		// 2nd page for claim
		WebElement claimButton = driver.findElement(By.cssSelector("button"));
		claimButton.click();
	}

}
