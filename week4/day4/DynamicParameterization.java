package week4.day4;

import java.io.IOException;
import java.time.Duration;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class DynamicParameterization {

	ChromeDriver driver;

	@DataProvider
	public String[][] testData() throws IOException {
		String filePath = "./Test_data/Legal_Entity_Data.xlsx";

		XSSFWorkbook wb = new XSSFWorkbook(filePath);

		XSSFSheet ws = wb.getSheet("Entity_Details");

		int rowCount = ws.getLastRowNum();

		short colCount = ws.getRow(1).getLastCellNum();

		ws.getRow(1).getCell(1);

		String[][] data = new String[rowCount][colCount];

		for (int i = 1; i <= rowCount; i++) {
			XSSFRow row = ws.getRow(i);
			for (int j = 0; j < colCount; j++) {
				String allData = row.getCell(j).getStringCellValue();
				data[i - 1][j] = allData;
				System.out.println(allData);
			}
		}
		wb.close();
		return data;
	}

	@BeforeMethod()
	@Parameters({ "url", "username", "password" })
	public void launchBrowser(String url, String uname, String pwd) {

		ChromeOptions options = new ChromeOptions();

		options.addArguments("--disable-notifications");

		driver = new ChromeDriver(options);

		// Login to https://login.salesforce.com
		driver.get(url);
		driver.manage().window().maximize();

		// Username: dilip@testleaf.com
		driver.findElement(By.id("username")).sendKeys(uname);

		// Password: testleaf@2024
		driver.findElement(By.id("password")).sendKeys(pwd);

		// Click login
		driver.findElement(By.id("Login")).click();

		// Implicit wait
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

	}

	@Test(dataProvider = "testData")
	public void salesforceActions(String entityName, String companyName, String Description) {
		// Click on the toggle menu button from the left corner
		driver.findElement(By.xpath("//div[@class='slds-icon-waffle']")).click();

		// Click View All and click Legal Entities from App Launcher
		driver.findElement(By.xpath("//button[text()='View All']")).click();

		// Click on the Dropdown icon in the legal Entities tab
		Actions subMenu = new Actions(driver);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		WebElement legalEntity = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//p[@class='slds-truncate' and text()='Legal Entities']")));
		subMenu.click(legalEntity).perform();

		// Click on New Legal Entity
		driver.findElement(By.className("forceActionLink")).click();

		// Enter legal entity name
		driver.findElement(By.xpath("//input[@name='Name']")).sendKeys(entityName);

		// Enter the Company name as 'TestLeaf'.
		driver.findElement(By.xpath("//input[@name='CompanyName']")).sendKeys(companyName);

		// Enter Description as 'Salesforces'.
		driver.findElement(By.xpath("(//textarea[@class='slds-textarea'])[2]")).sendKeys(Description);

		// Select Status as 'Active'
		WebElement statusDropdown = driver.findElement(By.xpath("//div[@class='slds-combobox_container']//button"));
		driver.executeScript("arguments[0].click();", statusDropdown);

		WebElement statusValue = driver.findElement(By.xpath("//span[@class='slds-truncate' and text()='Active']"));
		driver.executeScript("arguments[0].click();", statusValue);

		// Click on Save
		driver.findElement(By.xpath("//button[text()='Save']")).click();

		// Verify the Alert message (Complete this field) displayed for Name
		boolean toaster = driver.findElement(By.xpath("//div[contains(@class,'toastContent')]")).isDisplayed();
		String legalName = driver.findElement(By.xpath("//span[contains(@class,'toastMessage ')]/a/div")).getText();
		if (toaster) {
			System.out.println("Success confirmation is displayed!.. and the legal name is: " + legalName);
		} else {
			System.out.println("Success confirmation is not displayed!..");
		}
	}

	@AfterMethod
	public void quitBrowser() {
		driver.close();
	}

}
