package swapi;


import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.List;
import java.util.Map;

import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Utility.Constant;
import Utility.ExcelUtils;
import Utility.Log;
import Utility.Utils;




public class Test1 {
	public WebDriver driver;
	private String sTestCaseName;
	private int iTestCaseRow;
	private final int t1_p=1;
	



  @Test
  public void TestCase1() throws Exception {
	  int expected=200;
	  int planet_count=0,read_planet_count = 0;
	  String link="https://swapi.co/api/planets";
	  RestAssured.baseURI=link;
	  RequestSpecification reqspec=RestAssured.given(); 
	  Response response=reqspec.get();
	  //System.out.println(resp.getBody().asString());
	  if(expected==response.getStatusCode()){
		  Log.info("Response Code "+expected+" received");
		  JsonPath jsonpatheval=response.jsonPath();
		  planet_count=Integer.parseInt(jsonpatheval.get("count").toString());
		  Log.info("Total number of planet is ="+planet_count);
		  int i=2;
		  String next="";

		  do{
			  next=jsonpatheval.get("next")==null?"":jsonpatheval.get("next").toString();
			  if(next!="")
			  {
			  Log.info("Navigated to Next Page:"+next);
			  System.out.println("Navigated to Next Page:"+next);
			  //i++;
			  }
			  else
			  {
				  Log.info("No more page left after that");
			  }
			  List<Map<String, String>> planet=jsonpatheval.getList("results");
			  for(int j=0;j<planet.size();j++){
				  Log.info(planet.get(j).get("name"));
				  read_planet_count++;
			  }
			  

				  if(next!=""){
				  response=RestAssured.given().params("page",i).when().get(link);
				  jsonpatheval=response.jsonPath();
				  //Thread.sleep(5000);
				  //System.out.println(resp.getBody().asString());
				  if(expected==response.getStatusCode()){
					  i++;
					  
				  }
				  else
				  {
					  Log.info("Couldn't navigate to Next Page");
				  }
				  }  	  
			  
		  }while(next!="");
		  if(read_planet_count==planet_count)
		  {
			  Constant.ispassed=true;
			  ExcelUtils.setCellData(Integer.toString(planet_count), iTestCaseRow, Constant.Col_Exp_Result);
		  }
		  
	  }
	  else{
		  Log.error("Response Code Received is "+response.getStatusCode());
		  Constant.ispassed=false;
	  }
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
		} else

			Log.error("No test case with the Name " + sTestCaseName + "found");
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

}
