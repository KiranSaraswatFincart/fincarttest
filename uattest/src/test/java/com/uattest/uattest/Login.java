package com.uattest.uattest;

import java.util.List;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Login extends BaseTest {

    @Test
    public void testInvestorFlow() throws InterruptedException {
        driver.get("https://uat-dashboard.fincart.com/Login/");
        driver.findElement(By.name("username")).sendKeys("kevsharma.sharma@gmail.com");
        driver.findElement(By.name("password")).sendKeys("kewal12");
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        // Close popup if it appears
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@aria-label='Close']"))).click();
        waitForLoaderToDisappear();

        // Handle potential alert
        handleAlertIfPresent();

        // Navigate to UL Shares
        wait.until(ExpectedConditions.elementToBeClickable(By.id("radix-1"))).click();
        waitForLoaderToDisappear();

        List<WebElement> dropdownItems = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@role='menuitem']")));
        if (dropdownItems.size() > 7) {
            dropdownItems.get(7).click(); // Selecting "ULS"
        }
        waitForLoaderToDisappear();

        // Select first investor
        Thread.sleep(2000);        List<WebElement> investorList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("div.css-1n1vin2")));
        if (!investorList.isEmpty()) {
            investorList.get(0).click();
        } else {
            System.out.println("Investor list is empty.");
        }

        // Verify investor name
        String selectedInvestor = driver.findElement(By.cssSelector("div.css-hlgwow")).getText();
        Assert.assertEquals(selectedInvestor, "KEWAL SHARMA");

        // Proceed to Buy Now
        waitForLoaderToDisappear();
        driver.findElement(By.xpath("//p[normalize-space()='Buy Now']")).click();
        waitForLoaderToDisappear();

        driver.switchTo().frame(0);
        waitForLoaderToDisappear();

        WebElement ulsElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[text()='CSK Unlisted Shares']")));
        if (ulsElement.getText().equals("CSK Unlisted Shares")) {
            driver.findElement(By.xpath("//p[normalize-space()='Buy']")).click();
        }

        // Select goal from dropdown
        selectFromDropdown(By.cssSelector(".css-1n1vin2"), By.id("react-select-ID-listbox"));

        // Select investor again (2nd instance)
        selectFromDropdown(By.xpath("(//div[@class='text-edit css-b62m3t-container'])[2]"), By.id("react-select-ID-listbox"));

        // Enter amount
        driver.findElement(By.name("SelectedAmount")).sendKeys("10000");

        // Select CMR
        selectFromDropdown(By.xpath("(//div[@class=' css-hlgwow'])[3]"), By.id("react-select-ID-listbox"));

        // Upload file
        uploadCMR("C:/Users/FINCART/Downloads/Sample_CMR_with_PAN.pdf", "C:\\Users\\FINCART\\Downloads\\CMR-waybill-ocr.pdf");

        // Add to cart
        driver.findElement(By.xpath("//p[text()='Add to Cart']")).click();
    }

    private void handleAlertIfPresent() {
        try {
            Alert alert = driver.switchTo().alert();
            System.out.println("Alert text: " + alert.getText());
            alert.accept();
        } catch (NoAlertPresentException e) {
            System.out.println("No alert present.");
        }
    }

    private void selectFromDropdown(By dropdownTrigger, By listboxItems) {
        wait.until(ExpectedConditions.elementToBeClickable(dropdownTrigger)).click();
        List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(listboxItems));
        if (!options.isEmpty()) {
            options.get(0).click();
        } else {
            System.out.println("Dropdown is empty.");
        }
    }

    private void uploadCMR(String firstFile, String secondFile) {
        WebElement fileInput = driver.findElement(By.xpath("//input[@type='file']"));
        fileInput.sendKeys(firstFile);

        // Check for alert message
        try {
            WebElement alert = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("1onh2b9")));
            if (alert.getText().contains("invalid file type")) {
                driver.findElement(By.xpath("//input[@type='file']")).sendKeys(secondFile);
            }
        } catch (Exception e) {
            System.out.println("No file error alert displayed.");
        }
    }
}
