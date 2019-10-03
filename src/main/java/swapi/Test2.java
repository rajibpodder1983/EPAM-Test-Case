package swapi;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.WebDriver;
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

public class Test2 {
	public WebDriver driver;
	private String sTestCaseName;
	private int iTestCaseRow;
	String param=null;
  @Test
  public void Test2() throws Exception {
		int expected=200;
		String link="https://swapi.co/api/planets";
		RestAssured.baseURI=link;
		RequestSpecification reqspec=RestAssured.given(); 
		//param="4";
		Response response=reqspec.get(param);
		if(expected==response.getStatusCode()){
			  Log.info("Response Code "+expected+" received");
			  Constant.ispassed=true;
			  ExcelUtils.setCellData(response.getBody().asString(),iTestCaseRow,Constant.Col_Exp_Result);
		System.out.println(response.getBody().asString());	  
		}
		else
		{
			Log.error("Response Code Received is "+response.getStatusCode());
			  Constant.ispassed=false;
			  
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
	  DOMConfigurator.configure("log4j.xml");

		ExcelUtils.setExcelFile(
					Constant.Path_TestData + Constant.File_TestData, "Sheet1");
			Log.info("Excel File Setup completed.");
			

			sTestCaseName = Utils.getTestCaseName(this.toString());

			iTestCaseRow = ExcelUtils.getRowContains(sTestCaseName,
					Constant.Col_TestName);
			System.out.println("Test case row " + iTestCaseRow);

			if (iTestCaseRow != 0) {
				//driver = Utils.openBrowser(iTestCaseRow);
				Log.startTestCase(sTestCaseName);
				param=ExcelUtils.getCellData(this.iTestCaseRow, Constant.Col_Param);
			} else
			{
				Log.error("No test case with the Name " + sTestCaseName + "found");
			}
  }

  @AfterTest
  public void afterTest() throws Exception {
	  ExcelUtils.setExcelFile(
				Constant.Path_TestData + Constant.File_TestData, "Sheet1");
	  if (Constant.ispassed) {
			ExcelUtils.setCellData("Pass", iTestCaseRow, Constant.Col_Result);
			
		} else
			ExcelUtils.setCellData("Fail", iTestCaseRow, Constant.Col_Result);
		Log.endTestCase(sTestCaseName);
  }

  @BeforeSuite
  public void beforeSuite() {
  }

  @AfterSuite
  public void afterSuite() {
  }

}
