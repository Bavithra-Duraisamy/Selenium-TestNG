package testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class LeadActions {
	public ChromeDriver driver;

	@Parameters({"url", "username", "password" })
	@BeforeMethod
	public void preConditions(String url, String uname, String pword) {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(url);
		driver.findElement(By.id("username")).sendKeys(uname);
		driver.findElement(By.id("password")).sendKeys(pword);
		driver.findElement(By.className("decorativeSubmit")).click();
		driver.findElement(By.linkText("CRM/SFA")).click();
	}

	@Test
	public void createLead() {
		driver.findElement(By.linkText("Create Lead")).click();
		driver.findElement(By.id("createLeadForm_firstName")).sendKeys("Bavithra");
		driver.findElement(By.id("createLeadForm_lastName")).sendKeys("Duraisamy");
		driver.findElement(By.id("createLeadForm_companyName")).sendKeys("Ideas2IT");
		driver.findElement(By.id("createLeadForm_generalProfTitle")).sendKeys("Nurturebox");
		driver.findElement(By.name("submitButton")).click();

	}

	@Test
	public void editLead() throws InterruptedException {
		driver.findElement(By.linkText("Leads")).click();
		driver.findElement(By.linkText("Create Lead")).click();
		driver.findElement(By.xpath("(//input[@name='companyName'])[2]")).sendKeys("Test Leaf");
		driver.findElement(By.xpath("//input[@id='createLeadForm_firstName']")).sendKeys("Bavithra");
		driver.findElement(By.xpath("//input[@id='createLeadForm_lastName']")).sendKeys("Duraisamy");
		driver.findElement(By.xpath("//input[@id='createLeadForm_firstNameLocal']")).sendKeys("Bavi");
		driver.findElement(By.xpath("//input[@id='createLeadForm_departmentName']")).sendKeys("CSE");
		driver.findElement(By.xpath("//textarea[@id='createLeadForm_description']")).sendKeys("Senior Quality Analyst");
		driver.findElement(By.xpath("//input[@id='createLeadForm_primaryEmail']"))
				.sendKeys("bavithra.arvind@gmail.com");
		WebElement state = driver.findElement(By.id("createLeadForm_generalStateProvinceGeoId"));
		Select stateValue = new Select(state);
		stateValue.selectByVisibleText("New York");
		driver.findElement(By.name("submitButton")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("(//a[@class='subMenuButton'])[1]")).click();
		driver.findElement(By.xpath("//textarea[@id='createLeadForm_description']")).clear();
		driver.findElement(By.id("createLeadForm_importantNote")).sendKeys("Sample Description");
		driver.findElement(By.name("submitButton")).click();
		Thread.sleep(1000);
		String pageTitle = driver.getTitle();
		System.out.println(pageTitle);
	}

	@Test
	public void deleteLead() throws InterruptedException {
		driver.findElement(By.linkText("Leads")).click();
		driver.findElement(By.linkText("Find Leads")).click();
		driver.findElement(By.xpath("//span[contains(text(), 'Phone')]")).click();
		driver.findElement(By.name("phoneNumber")).sendKeys("0000000000");
		driver.findElement(By.xpath("//button[contains(text(),'Find Leads')]")).click();
		Thread.sleep(3000);
		String firstLead = driver.findElement(By.xpath("(//a[@class='linktext'])[4]")).getText();
		driver.findElement(By.xpath("(//a[@class='linktext'])[4]")).click();
		driver.findElement(By.className("subMenuButtonDangerous")).click();
		driver.findElement(By.linkText("Find Leads")).click();
		if (firstLead.isBlank()) {
			System.out.println("The first lead is not deleted");
		} else {
			System.out.println("The first lead is deleted successfully");
		}
		Thread.sleep(1000);
	}

	@AfterMethod
	public void postConditions() {
		driver.close();

	}

}
