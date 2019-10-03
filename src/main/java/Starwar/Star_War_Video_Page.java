package Starwar;

import java.util.List;

import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

import Utility.Constant;
import Utility.ExcelUtils;
import Utility.Log;
import Utility.Utils;

public class Star_War_Video_Page {
	public WebDriver driver;
	private String sTestCaseName;
	private int iTestCaseRow;
	String URL = null;
	String Expected_Title = null;
	int active_links = 0;
  @Test
  public void f() throws Exception {

		Expected_Title = ExcelUtils.getCellData(iTestCaseRow,
				Constant.Col_Exp_Result);
		driver = Utils.openBrowser(iTestCaseRow);
		String title = driver.getTitle();
		try {
			Assert.assertEquals(Expected_Title, title);
			// Now we will check if all the links in the Page is working as
			// expected?
			Thread.sleep(5000);

			List<WebElement> links = driver.findElements(By.tagName("a"));
			// To print the total number of links
			Log.info("Total links detected are :" + links.size());
			//ExcelUtils.setCellData(Integer.toString(links.size()),iTestCaseRow,Constant.Col_Exp_Result);

			for (int i = 0; i < links.size(); i++) {
				WebElement element = links.get(i);

				String url = element.getAttribute("href");

				active_links = active_links + Starwar_Utility.verifyLink(url);

			}
			if (active_links == links.size()) {
				
				
				Constant.ispassed = true;
			} else {
				Log.error("Expected Link Not matched with Active Link");
				Constant.ispassed = false;
			}
		} catch (AssertionError a) {
			Log.error("Page Title not matched with Expected Result");
			Constant.ispassed = false;
		}
  }
  @BeforeMethod
  public void beforeMethod() {
  }

  @AfterMethod
  public void afterMethod() {
  }

  @BeforeClass
  public void beforeClass() {
  }

  @AfterClass
  public void afterClass() {
  }

  @BeforeTest
  public void beforeTest() throws Exception {
	  System.setProperty("webdriver.firefox.marionette",
				"C:\\COS\\Selenium\\Libraries\\geckodriver-v0.8.0-win32\\geckodriver.exe");
		DOMConfigurator.configure("log4j.xml");

		ExcelUtils.setExcelFile(
				Constant.Path_TestData + Constant.File_TestData, "Sheet1");
		Log.info("Excel File Setup completed.");

		sTestCaseName = Utils.getTestCaseName(this.toString());

		iTestCaseRow = ExcelUtils.getRowContains(sTestCaseName,
				Constant.Col_TestName);
		System.out.println("Test case row " + iTestCaseRow);

		if (iTestCaseRow != 0) {
			// driver = Utils.openBrowser(iTestCaseRow);
			Log.startTestCase(sTestCaseName);

		} else {
			Log.error("No test case with the Name " + sTestCaseName + "found");
		}
  }

  @AfterTest
  public void afterTest() throws Exception {
	  Thread.sleep(5000);
		ExcelUtils.setExcelFile(
				Constant.Path_TestData + Constant.File_TestData, "Sheet1");
		if (Constant.ispassed) {
			ExcelUtils.setCellData("Pass", iTestCaseRow, Constant.Col_Result);

		} else
			ExcelUtils.setCellData("Fail", iTestCaseRow, Constant.Col_Result);
		Log.endTestCase(sTestCaseName);
		driver.close();
  }

  @BeforeSuite
  public void beforeSuite() {
  }

  @AfterSuite
  public void afterSuite() {
  }

}
