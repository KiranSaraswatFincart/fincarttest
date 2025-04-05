
package com.uattest.uattest;

import org.openqa.selenium.chrome.ChromeDriver;

public class AppTest{
	
	public static void main()
	{
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\FINCART\\Downloads\\chromedriver-win64\\chromedriver.exe");
		ChromeDriver driver=new ChromeDriver();
		driver.get("https://uat-workpoint.fincart.com/");
		

	}

}

