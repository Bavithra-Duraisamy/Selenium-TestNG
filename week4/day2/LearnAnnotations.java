package week4.day2;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class LearnAnnotations {

	@AfterMethod
	public void afterMethod() {
		System.out.println("After Method");

	}

	@AfterClass
	public void afterClass() {
		System.out.println("After Class");

	}

	@AfterTest
	public void afterTest() {
		System.out.println("After Test");

	}

	@AfterSuite
	public void afterSuite() {
		System.out.println("After Suite");

	}

	@Test
	public void testCase() {
		System.out.println("Test Case");

	}

	@BeforeMethod
	public void beforeMethod() {
		System.out.println("Before Method");

	}

	@BeforeTest
	public void beforeTest() {
		System.out.println("Before Test");

	}

	@BeforeClass
	public void beforeClass() {
		System.out.println("Before Class");

	}

	@BeforeSuite
	public void beforeSuite() {
		System.out.println("Before Suite");

	}

}
