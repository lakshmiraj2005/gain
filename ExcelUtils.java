package com.lexisnexis.risk.swqa.RIN.ui.test;






import java.io.FileInputStream;

import java.io.FileNotFoundException;

import java.io.FileOutputStream;

import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;

import org.apache.poi.xssf.usermodel.XSSFRow;

import org.apache.poi.xssf.usermodel.XSSFSheet;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

   public class ExcelUtils {

private static XSSFSheet ExcelWSheet;

private static XSSFWorkbook ExcelWBook;

private static XSSFCell Cell;

private static int Row;

public static Object[][] getTableArray(String FilePath, String SheetName) throws Exception {   

   String[][] tabArray = null;

   try {

   FileInputStream ExcelFile = new FileInputStream(FilePath);

   // Access the required test data sheet

   ExcelWBook = new XSSFWorkbook(ExcelFile);

   ExcelWSheet = ExcelWBook.getSheet(SheetName);

  

   int ci,cj;

   int totalRows = ExcelWSheet.getLastRowNum();

System.out.println(totalRows);
   // you can write a function as well to get Column count
XSSFRow row = ExcelWSheet.getRow(0);

 int totalCols=row.getLastCellNum();
 System.out.println(totalCols);

 tabArray=new String[totalRows][totalCols];

   ci=0;
  

   for (int i=1;i<=totalRows;i++, ci++) {              
	
	  
  cj=0;

   for (int j=0;j<totalCols;j++, cj++){
	
   tabArray[ci][cj]=getCellData(i,j);
 //  tabArray[ci][cj].setCellType(Cell.CELL_TYPE_STRING);
   System.out.println(tabArray[ci][cj]);  

}

}

}

catch (FileNotFoundException e){

System.out.println("Could not read the Excel sheet");

e.printStackTrace();

}

catch (IOException e){

System.out.println("Could not read the Excel sheet");

e.printStackTrace();

}

return(tabArray);

}

@SuppressWarnings("deprecation")
public static String getCellData(int RowNum, int ColNum) throws Exception {

try{

Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);



String CellData = Cell.getStringCellValue();

return CellData;

}catch (Exception e){

System.out.println(e.getMessage());

throw (e);

}

}

}