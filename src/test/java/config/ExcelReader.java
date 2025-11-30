package config;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

	public static Object[][] getTestData()
	{
		try {
			FileInputStream file = new FileInputStream("testdata/data1.xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheetAt(0);
			int totalRows = sheet.getPhysicalNumberOfRows();
			int totalColumns = sheet.getRow(0).getLastCellNum();
			
			Object[][] data = new Object[totalRows-1][totalColumns];
			System.out.println("Total Rows (including header): " + totalRows);
			
			int dataIndex = 0;
			for(int i=1;i<=totalRows;i++)
			{
				XSSFRow row = sheet.getRow(i);
				
				if (row == null || row.getCell(0) == null) {
			        System.out.println("Skipping empty row: " + i);
			        continue; // skip empty rows
			    }
				System.out.println(row);
				for(int j =0;j<totalColumns;j++)
				{
					data[dataIndex][j]= row.getCell(j).toString();
				}
				dataIndex++;
			}
			workbook.close();
			file.close();
			return data;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
