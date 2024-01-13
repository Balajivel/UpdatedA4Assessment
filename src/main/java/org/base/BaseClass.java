package org.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {

	public static WebDriver driver;
	public static ExtentTest test;
	public static ExtentReports extentReports;

	public static void browserLaunch(String browserName) {

		String propertiesFileDataAccess = propertiesFileDataAccess("browser");
		System.out.println(propertiesFileDataAccess);
		if (propertiesFileDataAccess.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		} else if (propertiesFileDataAccess.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		} else if (propertiesFileDataAccess.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		} else if (propertiesFileDataAccess.equalsIgnoreCase("safari")) {
			WebDriverManager.safaridriver().setup();
			driver = new SafariDriver();
		}

	}

	public static void urlLaunch(String url) {

		driver.get(url);

	}

	public static String gettingCurrentURL() {
		return driver.getCurrentUrl();

	}

	public static void waitEachElementToBeLoaded() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

	}

	public static void maxBrowser() {
		driver.manage().window().maximize();

	}

	public static void browserBackwardNavigation() {
		driver.navigate().back();

	}

	public static void closeBrowser() {
		driver.quit();

	}

	public static void closeTab() {
		driver.close();

	}

	public static String propertyValue;

	public static String propertiesFileDataAccess(String property) {
		Properties properties = new Properties();
		try {
			FileInputStream fileInputStream = new FileInputStream(
					System.getProperty("user.dir") + "\\Properties-Files\\config.properties");
			properties.load(fileInputStream);
			propertyValue = properties.getProperty(property);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return propertyValue;

	}
	
	public static String propertiesFileEnvAccess(String property, String propertiesFileName) {
		Properties properties = new Properties();
		try {
			FileInputStream fileInputStream = new FileInputStream(
					System.getProperty("user.dir") + "\\Properties-Files\\"+propertiesFileName+".properties");
			properties.load(fileInputStream);
			propertyValue = properties.getProperty(property);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return propertyValue;

	}

	public static void generateExtentReport(String testName, String description) {
		ExtentSparkReporter extentSparkReporter = new ExtentSparkReporter("extentReport.html");
		try {
			extentReports = new ExtentReports();
			extentReports.attachReporter(extentSparkReporter);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		test = extentReports.createTest(testName, description);

	}

	public static void setupBrowserAndLaunchURL() {
		
		browserLaunch(propertyValue);
		test.pass("Chrome Browser Started");

		urlLaunch(propertiesFileDataAccess("url"));
		test.pass("Sauce Demo website launched");

		waitEachElementToBeLoaded();
		maxBrowser();
		test.pass("Browser Maximized");

	}

	public List<Map<String, String>> readExcelAndStoreInMap(String filePath) {

		List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();

		FileInputStream fis = null;
		Workbook workbook = null;
		try {
			fis = new FileInputStream(new File(filePath));
			workbook = new XSSFWorkbook(fis);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// For .xlsx files

		String sheetName = propertiesFileDataAccess("excel_sheet_name");
		Sheet sheet = workbook.getSheet(sheetName);

		Row headerRow = sheet.getRow(0); 
		int lastCellNum = headerRow.getLastCellNum();

		for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
			Row row = sheet.getRow(rowNum);

			if (row != null) {
				Map<String, String> dataMap = new HashMap<String, String>();

				for (int cellNum = 0; cellNum < lastCellNum; cellNum++) {
					Cell headerCell = headerRow.getCell(cellNum);
					Cell dataCell = row.getCell(cellNum);

					String header = headerCell.getStringCellValue().trim();
					int cellType = dataCell.getCellType();
					String value = "";
					if (cellType == 1) {
						value = (dataCell != null) ? dataCell.getStringCellValue().trim() : "";
					} else {
						double numericCellValue = dataCell.getNumericCellValue();
						long l = (long) numericCellValue;
						value = String.valueOf(l);
					}

					dataMap.put(header, value);
				}

				dataList.add(dataMap);
			}
		}

		try {
			fis.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return dataList;
	}

	public String accessDataFromExcelBasedOnHeader(String header) {

		String excelFileName = propertiesFileDataAccess("excel_file_name");

		String excelFilePath = System.getProperty("user.dir") + "/" + excelFileName + ".xlsx";

		List<Map<String, String>> dataList = readExcelAndStoreInMap(excelFilePath);

		String value = "";
		for (Map<String, String> data : dataList) {
			String text = data.get(header);
			value = text;

		}
		return value;

	}
	public static String readExcel(String headerValue) throws IOException {
		List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
		String excelFileName = propertiesFileDataAccess("excel_file_name");
		FileInputStream fis = new FileInputStream(new File(System.getProperty("user.dir") + "/" + excelFileName + ".xlsx"));
		Workbook workbook = new XSSFWorkbook(fis); 
		Sheet sheet = workbook.getSheet("Registration-form"); 
		Row headerRow = sheet.getRow(0); 
		int lastCellNum = headerRow.getLastCellNum(); 
	for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) { 
		Row row = sheet.getRow(rowNum); if (row != null) { 
			Map<String, String> dataMap = new HashMap<String, String>(); 
			for (int cellNum = 0; cellNum < lastCellNum; cellNum++) { 
				Cell headerCell = headerRow.getCell(cellNum); 
				Cell dataCell = row.getCell(cellNum); 
				String header = headerCell.getStringCellValue().trim(); 
				int cellType = dataCell.getCellType(); 
				String value = ""; 
				if (cellType == 1) { 	
					value = (dataCell != null) ? dataCell.getStringCellValue().trim() : "";
					}else{						
						double numericCellValue = dataCell.getNumericCellValue();
						long l = (long) numericCellValue;				
						value = String.valueOf(l);					
						} 
				dataMap.put(header, value); 
				} dataList.add(dataMap); 
				} 
		}
	fis.close(); 
	List<Map<String, String>> dataList2 = dataList;
	for (Map<String, String> map : dataList2) {
		String value = map.get(headerValue);
		return value;
	}
	return headerValue;
	}
}

