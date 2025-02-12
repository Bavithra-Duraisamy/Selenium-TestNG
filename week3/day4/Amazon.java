package week3.day4;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class Amazon {

	// Initialize ChromeDriver
	static ChromeDriver driver = new ChromeDriver();
	String parentWindow = driver.getWindowHandle();

	@Test
	public void amazonActions() throws IOException {

		// Load the URL (http://leaftaps.com/opentaps/control/login)
		driver.get("https://www.amazon.in/");

		// Maximize the browser window
		driver.manage().window().maximize();

		// Add an implicit wait to ensure the webpage elements are fully loaded
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		// Search for "oneplus 9 pro"
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys("oneplus 9 pro");
		driver.findElement(By.id("nav-search-submit-button")).click();

		// Get the price of the first product.
		String price = driver.findElement(By.xpath("(//span[@class='a-offscreen'])[1]")).getText();
		System.out.println("Price of the first product is: " + price);

		// Print the number of customer ratings for the first displayed product.
		String ratings = driver.findElement(By.xpath("//a[contains(@class, 'a-link-normal')]/span")).getText();
		System.out.println("Number of ratings for the first product is: " + ratings);

		// Click the first text link of the first image.
		driver.findElement(By.xpath("(//div[contains(@class, 's-image-fixed-height')]/img)[1]")).click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		switchToChildWindow();

		// Take a screenshot of the product displayed.
		WebElement productImage = driver.findElement(By.className("imgTagWrapper"));
		File source = productImage.getScreenshotAs(OutputType.FILE);
		File destination = new File("./ScreenShot/product.png");
		FileUtils.copyFile(source, destination);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		// Click the 'Add to Cart' button.
		Actions addToCart = new Actions(driver);
		addToCart.scrollToElement(driver.findElement(By.name("submit.add-to-cart"))).perform();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.name("submit.add-to-cart")));
		addToCartButton.click();

		// Get the cart subtotal and verify if it is correct.
		String subTotal = driver.findElement(By.xpath("//span[contains(@id, 'sc-subtotal-amount')]")).getText();
		subTotal.replace("₹", "");
		if (price.equalsIgnoreCase(subTotal)) {
			System.out.println("Price and the Sub total matches for the product added into the cart");
		} else {
			System.out.println("Price and the Sub total are not matches for the product added into the cart");
		}

		// 9. Close the browser.
		quitBrowser();

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

	public static void main(String[] args) throws IOException {
		Amazon amazon = new Amazon();
		amazon.amazonActions();

	}

}
