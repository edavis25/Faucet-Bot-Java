import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Bot {

	// ***** CHANGE TO DICTATE NUMBER OF CLAIMS TO MAKE *****
	static final int NUMBER_OF_CLAIMS_DESIRED = 5000;

	static WebDriver driver;
	static WebDriver driver2;

	static WebElement randomizeIPButton;

	// Input multiple Bitcoin addresses into array to use (optional)
	static String[] walletAddressArray = { ***INSERT WALLET ADDRESSES INTO ARRAY*** };
	static int index = 0;

	public static void main(String[] args)
	{
		// Setup to use the chromedriver.exe
		System.setProperty("webdriver.chrome.driver", "./chromedriver_win32/chromedriver.exe");

		// Driver controls the web browser as if a human.
		driver = new ChromeDriver();

		// Open 1st driver and login to VPN (optional)
		driver.get("https://vpnme.com/");
		vpnLogin();


		// --- Main execution --->
		// Test counter to stop infinite loop.
		int testStopCounter = 0;

		while (testStopCounter < NUMBER_OF_CLAIMS_DESIRED)
		{

			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-images");

			driver2 = new ChromeDriver(options);

			// Navigate to the faucet
			driver2.get("http://neonbit.cf?ref=3981");

			// Use faucet and logout
			useBitcoinFaucet();

			// Close the driver (browser)
			driver2.quit();

			// Increment counters
			testStopCounter++;
			index++;

			// Restart address being input to the first address in the array once the last address has been used.
			if (index == walletAddressArray.length)
			{
				index = 0;
			}

			// Randomize IP (optional)
			randomizeIPButton.click();
		}
	}


	/*
	*	useBitcoinFaucet() - Navigate through faucet page. Enter address, claim reward, then logout. Elements and actions will
	*						 vary depending on whatever faucet is used.
	*/
	public static void useBitcoinFaucet()
	{
		// Enter address & login for homepage
		WebElement bitcoinAddressBox = driver2.findElement(By.className("form-control"));
		WebElement submitButton = driver2.findElement(By.cssSelector("button.btn.btn-primary.submit-input.wow.bounceInRight.animated.animated"));

		// Wait for element to be clickable to avoid exception
		WebDriverWait homeWait = new WebDriverWait(driver2, 10);
		homeWait.until(ExpectedConditions.elementToBeClickable(submitButton));

		bitcoinAddressBox.sendKeys(walletAddressArray[index]);
		submitButton.click();

		// Click claim button on confirmation page
		WebElement claimButton = driver2.findElement(By.cssSelector("button"));

		WebDriverWait wait = new WebDriverWait(driver2, 5);
		wait.until(ExpectedConditions.elementToBeClickable(claimButton));


		claimButton.click();

		// Logout
		WebElement logoutButton = driver2.findElement(By.cssSelector("a.wow.fadeInRight.animated"));

		logoutButton.click();
	}


	/*
	*	vpnLogin() - Navigate to the VPN and login to account. The elements and actions will vary on whatever
	*				 VPN and credentials are desired.
	*/
	public static void vpnLogin()
	{
		// Get & click login button on homepage
		WebElement vpnLoginButton = driver.findElement(By.linkText("Login"));	//<-- Find & cast to variable
		vpnLoginButton.click();

		// Enter email and password
		WebElement email = driver.findElement(By.id("loginEmail"));
		WebElement password = driver.findElement(By.id("loginPassword"));
		WebElement submitButton = driver.findElement(By.cssSelector("button.btn.btn-lg.btn-block.btn-primary"));

		email.sendKeys( ***** INSERT EMAIL ADDRESS *****);
		password.sendKeys( ***** INSERT PASSWORD *****);
		submitButton.click();

		// Cast the randomize ip button for future usage
		randomizeIPButton = driver.findElement(By.cssSelector("button.btn.btn-default.btn-block"));
	}

}
