package Utility;


import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
	private static XSSFSheet ExcelWSheet;
	private static XSSFWorkbook ExcelWBook;
	private static XSSFCell Cell;
	private static XSSFRow Row;
	//This method is to set the File path and to open the Excel file, Pass Excel Path and Sheetname as Arguments to this method
public static void setExcelFile(String Path,String SheetName) throws Exception {
		try {
			// Open the Excel file
		FileInputStream ExcelFile = new FileInputStream(Path);
		System.out.println(Path);
		// Access the required test data sheet
		ExcelWBook = new XSSFWorkbook(ExcelFile);
		ExcelWSheet = ExcelWBook.getSheet(SheetName);
		} catch (Exception e){
			throw (e);
		}
}

//This method is to read the test data from the Excel cell, in this we are passing parameters as Row num and Col num
public static String getCellData(int RowNum, int ColNum) throws Exception{
		try{
			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
			/*String CellData = String.valueOf(Cell.getNumericCellValue());
			return CellData;
			}catch (Exception e){
			return"";
			}*/
			
			if (Cell != null)
			{
				switch (Cell.getCellType())
				{
					case XSSFCell.CELL_TYPE_BLANK:
						return null;
					case XSSFCell.CELL_TYPE_BOOLEAN:
						return String.valueOf( Cell.getBooleanCellValue() );
					case XSSFCell.CELL_TYPE_NUMERIC:
						return String.valueOf( ( int ) Cell.getNumericCellValue() );
					case XSSFCell.CELL_TYPE_STRING:
						return Cell.getRichStringCellValue().toString();

				}
			}
			return null;}
		catch(Exception e){
			return "";
		}
}

//This method is to write in the Excel cell, Row num and Col num are the parameters
public static void setCellData(String Result,  int RowNum, int ColNum) throws Exception	{
		try{
			Row  = ExcelWSheet.getRow(RowNum);
		Cell = Row.getCell(ColNum, Row.RETURN_BLANK_AS_NULL);
		if (Cell == null) {
			Cell = Row.createCell(ColNum);
			Cell.setCellValue(Result);
			} else {
				Cell.setCellValue(Result);
			}

// Constant variables Test Data path and Test Data file name
				FileOutputStream fileOut = new FileOutputStream(Utility.Constant.Path_TestData + Utility.Constant.File_TestData);
				ExcelWBook.write(fileOut);
				fileOut.flush();
				fileOut.close();
			}catch(Exception e){
				throw (e);
		}

}


public static int getRowContains(String sTestCaseName, int colNum) throws Exception{
	int i;
    try {
	    int rowCount = ExcelWSheet.getLastRowNum();
        for ( i=1 ; i<=rowCount; i++){
	        if  (ExcelUtils.getCellData(i,colNum).equalsIgnoreCase(sTestCaseName)){
	        	return i;
	           }
	        }
        return 0;
    }catch (Exception e){
	    //Log.error("Class ExcelUtil | Method getRowContains | Exception desc : " + e.getMessage());
        throw(e);
	    }
    }

}
