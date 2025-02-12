package week3.day4;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

public class BigBasket {

	String parentWindow = driver.getWindowHandle();

	// Initialize ChromeDriver
	static ChromeDriver driver = new ChromeDriver();

	@Test
	public void BigBasketActions() throws InterruptedException, IOException {
		// Load the URL (https://www.bigbasket.com/)
		driver.get("https://www.bigbasket.com/");

		// Maximize the browser window
		driver.manage().window().maximize();

		// Add an implicit wait to ensure the webpage elements are fully loaded
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		// Click on "Shop by Category".
		driver.findElement(By.xpath("(//button[@aria-haspopup='true' and @aria-expanded='false'])[4]")).click();
		Thread.sleep(3000);

		// Mouse over "Foodgrains, Oil & Masala".
		Actions menu = new Actions(driver);
		menu.moveToElement(driver.findElement(By.linkText("Foodgrains, Oil & Masala"))).perform();

		// Mouse over "Rice & Rice Products".
		menu.moveToElement(driver.findElement(By.linkText("Rice & Rice Products"))).perform();

		// Click on "Boiled & Steam Rice".
		menu.moveToElement(driver.findElement(By.linkText("Boiled & Steam Rice"))).click().perform();
		Thread.sleep(5000);

		// Filter the results by selecting the brand "bb Royal".
		menu.moveToElement(driver.findElement(By.xpath("//label[@for='i-BBRoyal']"))).click().perform();
		Thread.sleep(5000);

		// Click on "Tamil Ponni Boiled Rice".
		menu.moveToElement(driver.findElement(By.xpath("//h3[text()='Tamil Ponni Boiled - Rice']"))).click().perform();
		switchToChildWindow();
		Thread.sleep(5000);

		// Select the 5 Kg bag.
		List<WebElement> packSize = driver.findElements(By.xpath("//span[contains(@class,'PackSizeSelector')]"));
		for (WebElement specificPackSize : packSize) {
			String sizeName = specificPackSize.getText();
			if (sizeName.equals("5 kg")) {
				specificPackSize.click();
				System.out.println(sizeName);
			}

		}

		// Check and note the price of the rice.
		String specificPackPrice = driver.findElement(By.xpath("(//tr[contains(@class,'items-center')])[2]/td"))
				.getText();
		System.out.println("The selected rice pack's price is: " + specificPackPrice);

		// Click "Add" to add the bag to your cart.
		driver.findElement(By.xpath("(//button[text()='Add to basket'])[1]")).click();

		// Verify the success message that confirms the item was added to your cart.
		String successMessage = driver
				.findElement(By.xpath("//p[contains(text(), 'An item has been added to your basket successfully')]"))
				.getText();
		if (successMessage.equalsIgnoreCase("An item has been added to your basket successfully")) {
			System.out.println("Product has been added to the cart successfully");
		} else {
			System.out.println("Product is not added. Click on Add to Cart again!..");
		}

		// Take a snapshot of the current page
		WebElement productImage = driver.findElement(By.id("siteLayout"));
		File source = productImage.getScreenshotAs(OutputType.FILE);

		// Define the destination file path
		File destination = new File("./ScreenShot/product.png");

		// Copy the screenshot to the destination file
		FileUtils.copyFile(source, destination);

		// Close the current window.
		driver.close();

		// Close the main window.
		driver.switchTo().window(parentWindow);
		driver.close();
	}

	public void switchToChildWindow() {
		Set<String> allWindows = driver.getWindowHandles();
		for (String window : allWindows) {
			if (!window.equals(parentWindow)) {
				driver.switchTo().window(window);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
				break;
			}
		}
	}

	public void quitBrowser() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		driver.quit();
	}

}
