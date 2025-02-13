package week3.day4;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class Snapdeal {

	// Initialize ChromeDriver
	static ChromeDriver driver = new ChromeDriver();

	@Test
	public void snapDealActions() throws IOException {

		// Load the URL (https://www.snapdeal.com/)
		driver.get("https://www.snapdeal.com/");

		// Maximize the browser window
		driver.manage().window().maximize();

		// Add an implicit wait to ensure the webpage elements are fully loaded
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		checkCookiePopup();

		// Go to "Men's Fashion".
		Actions subMenu = new Actions(driver);
		subMenu.moveToElement(driver.findElement(By.xpath("//span[text()=\"Men's Fashion\"]"))).perform();

		// Go to "Sports Shoes".
		subMenu.moveToElement(driver.findElement(By.xpath("(//span[text()=\"Sports Shoes\"])[1]"))).click().perform();

		// Get the count of sports shoes.
		int totalNumberOfShoes = driver.findElements(By.className("product-tuple-image")).size();
		System.out.println(totalNumberOfShoes);

		// Click on "Training Shoes".
		driver.findElement(By.xpath("//div[text()='Training Shoes']")).click();

		// Sort the products by "Low to High".
		driver.findElement(By.xpath("//div[contains(@class,'sort-drop')]")).click();
		driver.findElement(By.xpath("//li[contains(., 'Low To High')]")).click();

		// Check if the displayed items are sorted correctly.
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions
				.stalenessOf(driver.findElement(By.xpath("//span[contains(@class,'product-price')]")))); // Wait for old
																											// elements
																											// to go
																											// stale

		List<WebElement> productPrice = driver.findElements(By.xpath("//span[contains(@class,'product-price')]"));
		for (WebElement price : productPrice) {
			// String stringPrice = price.toString();
			System.out.println(price.getText());
		}

		// Select any price range ex:(500-700).
		driver.findElement(By.name("fromVal")).clear();
		driver.findElement(By.name("fromVal")).sendKeys("500");
		// Actions keyboardActions = new Actions(driver);
		subMenu.keyDown(Keys.ENTER);
		driver.findElement(By.name("toVal")).clear();
		driver.findElement(By.name("toVal")).sendKeys("1000");
		subMenu.keyDown(Keys.ENTER);

		// Filter by any colour.
		WebElement element = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText("White & Blue")));
		element = driver.findElement(By.partialLinkText("White & Blue"));
		subMenu.moveToElement(element).click().perform();

		// Verify all the applied filters.
		List<WebElement> appliedFilters = driver.findElements(By.xpath("//div[@class='navFiltersPill']/a"));
		for (WebElement filterValues : appliedFilters) {
			// String filters = filterValues.toString();
			System.out.println(filterValues.getText());
		}

		// Mouse hover on the first resulting "Training Shoes".
		List<WebElement> filteredProducts = driver.findElements(By.className("picture-elem"));
		if (!filteredProducts.isEmpty()) {
			WebElement firstProduct = filteredProducts.get(0);
			subMenu.moveToElement(firstProduct).perform(); // Mouse hover
			System.out.println("Hovered over the first element.");
		} else {
			System.out.println("No elements found with class 'picture-elem'");
		}

		// Click the "Quick View" button.
		driver.findElement(By.xpath("(//div[contains(@class,'quick-view-bar')])[1]")).click();

		// Print the cost and the discount percentage.
		String originalPrice = driver.findElement(By.className("payBlkBig")).getText();
		System.out.println("Original price is: " + originalPrice);
		String discountedPercentage = driver.findElement(By.cssSelector(".percent-desc")).getText();
		System.out.println("Discounted percentage is: " + discountedPercentage);

		// Take a snapshot of the shoes.
		WebElement productImage = driver.findElement(By.xpath("//img[@class='cloudzoom' and @itemprop='image']"));
		File source = productImage.getScreenshotAs(OutputType.FILE);

		// Define the destination file path
		File destination = new File("./ScreenShot/shoeimage.png");

		// Copy the screenshot to the destination file
		FileUtils.copyFile(source, destination);

		// Close the current window.
		driver.close();
	}

	public void checkCookiePopup() {
		boolean popupStatus = driver.findElement(By.className("push-container")).isDisplayed();
		if (popupStatus) {
			driver.findElement(By.id("pushAllow")).click();
		}
	}

}
