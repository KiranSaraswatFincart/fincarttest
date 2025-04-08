package com.uattest.uattest;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Login extends BaseTest {

    @Test
    public void testInvestorFlow() throws InterruptedException {
        driver.get("https://uat-dashboard.fincart.com/Login/");
        driver.findElement(By.name("username")).sendKeys("kevsharma.sharma@gmail.com");
        driver.findElement(By.name("password")).sendKeys("kewal12");
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@aria-label='Close']"))).click();
        waitForLoaderToDisappear();

        try {
            Alert alert = driver.switchTo().alert();
            System.out.println("Alert text: " + alert.getText());
            alert.accept();
        } catch (NoAlertPresentException e) {
            System.out.println("No alert found.");
        }

        WebElement invest = wait.until(ExpectedConditions.elementToBeClickable(By.id("radix-1")));
        invest.click();
        waitForLoaderToDisappear();
        System.out.println("Clicked on invest dropdown");
        List<WebElement> dropdownItems = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@role='menuitem']")));
        dropdownItems.get(7).click();
        waitForLoaderToDisappear();
        System.out.println("Clicked on UlS dropdown");
        Thread.sleep(4000);
        List<WebElement> investorList = wait.until(
        	    ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class=' css-1n1vin2']"))
        	);

        	if (!investorList.isEmpty()) {
        	    investorList.get(0).click();
        	    System.out.println("Clicked on the first investor element.");
        	} else {
        	    System.out.println("Investor list is empty. No element to click.");
        	}
     

        String selectedInvestor = driver.findElement(By.xpath("//div[@class=' css-hlgwow']")).getText();
        Assert.assertEquals(selectedInvestor, "KEWAL SHARMA");
        System.out.println("Clicked on invester name");
        driver.findElement(By.xpath("//p[text()='Buy More']")).click();
        
        //Switch to next frame for buying stocks
        waitForLoaderToDisappear();
       // driver.findElement(By.xpath("//p[normalize-space()='Buy Now']")).click();
        driver.switchTo().frame(0);
        System.out.println("Switched to frame");
      

        String uls = driver.findElement(By.xpath("//p[text()='CSK Unlisted Shares']")).getText();
        if (uls.equals("CSK Unlisted Shares")) {
            driver.findElement(By.xpath("//p[normalize-space()='Buy']")).click();
        } else {
            System.out.println("Not the same ULS");
        }
    List< WebElement> goals=(List<WebElement>) driver.findElement(By.id("react-select-ID-placeholder"));
     goals.get(0).click();
     
    }
    
}
