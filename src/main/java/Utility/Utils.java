package Utility;

import java.util.concurrent.TimeUnit;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;



public class Utils {
	public static WebDriver driver = null;
	 
    public static WebDriver openBrowser(int iTestCaseRow) throws Exception{
 
        String sBrowserName;
 
        try{
        	System.setProperty("webdriver.firefox.marionette","C:\\COS\\Selenium\\Libraries\\geckodriver-v0.8.0-win32\\geckodriver.exe");	
        sBrowserName = ExcelUtils.getCellData(iTestCaseRow, Constant.Col_Browser);
 
        if(sBrowserName.equals("Mozilla")){
 
            driver = new FirefoxDriver();
 
            //Log.info("New driver instantiated");
 
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
 
            //Log.info("Implicit wait applied on the driver for 10 seconds");
 
            driver.get(ExcelUtils.getCellData(iTestCaseRow, Constant.Col_URL));
 
            //Log.info("Web application launched successfully");
 
            }
 
        }catch (Exception e){
 
            //Log.error("Class Utils | Method OpenBrowser | Exception desc : "+e.getMessage());
 
        }
 
        return driver;
 
    }
    
    
    public static String getTestCaseName(String sTestCase)throws Exception{
    	 
        String value = sTestCase;
 
        try{
 
            int posi = value.indexOf("@");
 
            value = value.substring(0, posi);
 
            posi = value.lastIndexOf(".");    
 
            value = value.substring(posi + 1);
 
            return value;
 
                }catch (Exception e){
 
            //Log.error("Class Utils | Method getTestCaseName | Exception desc : "+e.getMessage());
 
            throw (e);
                }
    }
    
   

   
 

}
