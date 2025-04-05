package com.uattest.uattest;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class Login {

	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\FINCART\\Downloads\\chromedriver-win64\\chromedriver.exe");

		ChromeDriver driver = new ChromeDriver();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		driver.manage().window().maximize();

		try {
			// Open login page
			driver.get("https://uat-dashboard.fincart.com/Login/");

			// Login credentials
			driver.findElement(By.xpath("//input[@name='username']")).sendKeys("kevsharma.sharma@gmail.com");
			driver.findElement(By.xpath("//input[@name='password']")).sendKeys("kewal12");
			driver.findElement(By.xpath("//button[@type='submit']")).click();

			// Close popup if present
			WebElement closeButton = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@aria-label='Close']")));
			closeButton.click();

			// Wait until loader appears (this ensures we catch the second loader too)
			// wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='loading']")));

			// Wait until loader is completely gone
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='loading']")));
			// Visibility
			// wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[class*='Loader_loaderStyle']")));

			// Invisibility
			wait.until(ExpectedConditions
					.invisibilityOfElementLocated(By.cssSelector("button[class*='Loader_loaderStyle']")));

			// Wait until loader is completely gone
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='loading']")));

//             Now click on the Invest element
			WebElement invest = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='radix-1']")));
			invest.click();
			System.out.println("Clicked on Invest");

			// Click on 8th item in dropdown (index 7)
			List<WebElement> dropdownItems = wait
					.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@role='menuitem']")));
			dropdownItems.get(7).click();
			System.out.println("Clicked on Unlisted Stocks");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='loading']")));
			// Select investor
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class=' css-1n1vin2']")));
			List<WebElement> investorList = driver.findElements(By.xpath("//div[@class=' css-1n1vin2']"));
			investorList.get(0).click();
			System.out.println("Get Investor");

			// Validate selected investor name
			String selectedInvestor = driver.findElement(By.xpath("//div[@class=' css-hlgwow']"))
					.getText();
			System.out.println("Investor name fetched");

			Assert.assertEquals(selectedInvestor, "KEWAL SHARMA", "Investor name does not match!");
			System.out.println("Investor name verified");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='loading']")));

		//Navigate to unlisted purchase page using buy now button
			driver.findElement(By.xpath("//p[normalize-space()='Buy Now']")).click();
			driver.switchTo().frame(0);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='loading']")));

			String ULS=driver.findElement(By.xpath("//p[normalize-space()='CSK Unlisted Shares']")).getText();
			if(ULS.equals("CSK Unlisted Shares")) {
				driver.findElement(By.xpath("//p[normalize-space()='Buy']")).click();
			}
			else
			{
				System.out.print("Not the same ULS");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			}
	}
}
