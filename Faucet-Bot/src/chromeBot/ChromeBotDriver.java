package chromeBot;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



public class ChromeBotDriver {

	static int NUMBER_OF_CLAIMS_DESIRED = 50;

	static ChromeDriver VPNdriver;

	static WebElement randomizeIPButton;

	static ArrayList<ChromeBot> botList = new ArrayList<ChromeBot>();

	// Number of addresses in array determine how many bots will be created. (1 address per bot)
	static ArrayList<String> addressArray = new ArrayList<String>();

	static String referralURL1 = "http://neonbit.cf?ref=3981";
	
	
	/*
	 * main() - Login to VPN and randomize IP. For each list in the wallet address array, create a new bot inside of
	 * 			the botList array list. This array list is then used when creating threads and starting bot's execution.
	 */
	public static void main(String[] args)
	{
		readAddressFile(addressArray);
		userPrompt();
		vpnLogin();
		randomizeIP();

		for (int i = 0, length = addressArray.length; i < length; i++)
		{
			botList.add(new ChromeBot(addressArray[i], referralURL1, NUMBER_OF_CLAIMS_DESIRED));
			Thread thread = new Thread(botList.get(i));
			thread.start();
			randomizeIP();
		}
	}


	/*
	 * vpnLogin() - Navigate and login to VPN site. Cast randomize IP button to class variable.
	 */
	private static void vpnLogin()
	{
		System.setProperty("webdriver.chrome.driver", "./chromedriver_win32/chromedriver.exe");

		VPNdriver = new ChromeDriver();
		VPNdriver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);

		VPNdriver.get("https://vpnme.com/");

		// Get & click login button on homepage
		WebElement vpnLoginButton = VPNdriver.findElement(By.linkText("Login"));	//<-- Find & cast to variable
		vpnLoginButton.click();

		// Enter email and password
		WebElement email = VPNdriver.findElement(By.id("loginEmail"));
		WebElement password = VPNdriver.findElement(By.id("loginPassword"));
		WebElement submitButton = VPNdriver.findElement(By.cssSelector("button.btn.btn-lg.btn-block.btn-primary"));

		email.sendKeys(***VPN EMAIL***);
		password.sendKeys(***VPN PASSWORD***);
		submitButton.click();

		// Cast the randomize ip button for future usage
		WebDriverWait ipButtonWait = new WebDriverWait(VPNdriver, 120);
		ipButtonWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.btn.btn-default.btn-block")));

		randomizeIPButton = VPNdriver.findElement(By.cssSelector("button.btn.btn-default.btn-block"));
	}


	/*
	 * randomizeIP() - Clicks the randomize IP button and forces wait to allow for register.
	 */
	private static void randomizeIP()
	{
		randomizeIPButton.click();

		// Delay to let IP change register
		try
		{
			Thread.sleep(5000);
		}
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/*
	 * readAddressFile() - Read address file into the address array list.
	 */
	private static void readAddressFile(ArrayList<String> list) throws FileNotFoundException
	{
		File file = new File("./WALLET-ADDRESS-CONFIG.txt");
		
		Scanner scan = new Scanner(file);
		
		while (scan.hasNextLine())
		{
			list.add(scan.nextLine());
		}
		
		scan.close();
	}
	
	
	/*
	 * userPrompt() - Prompt for user input for number of claims each bot should make.
	 */
	private static void userPrompt()
	{

		Boolean done = false;

		while (!done)
		{
			try
			{
				String stringInput = (JOptionPane.showInputDialog("Enter number of claims you want each bot to make:"));
				// Quit on cancel
				if (stringInput == null) {System.exit(0);}
				
				NUMBER_OF_CLAIMS_DESIRED = Integer.parseInt(stringInput);
				
				if (NUMBER_OF_CLAIMS_DESIRED <= 0)
				{
					throw new NumberFormatException();
				}
				
				done = true;
			}
			catch(NumberFormatException e)
			{
				JOptionPane.showMessageDialog(null, "Enter only an intger greater than 0");
			}

		}
	}
}
