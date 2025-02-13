package week4.day4;

import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class readData {

	public static String[][] readEntityData(String fileName) throws IOException {

		String filePath = "./Test_data/" + fileName + ".xlsx";

		XSSFWorkbook wb = new XSSFWorkbook(filePath);

		XSSFSheet ws = wb.getSheet("Entity_Details");

		int rowCount = ws.getLastRowNum();

		short colCount = ws.getRow(1).getLastCellNum();

		ws.getRow(1).getCell(1);

		String[][] data = new String[rowCount][colCount];

		for (int i = 1; i <= rowCount; i++) {
			XSSFRow row = ws.getRow(i);
			for (int j = 0; j < colCount; j++) {
				String allData = row.getCell(j).getStringCellValue();
				data[i - 1][j] = allData;
				System.out.println(allData);
			}
		}
		wb.close();
		return data;
	}

}
