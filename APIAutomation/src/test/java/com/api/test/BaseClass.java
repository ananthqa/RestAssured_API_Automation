package com.api.test;

import org.aeonbits.owner.ConfigFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import propertiesFile.FrameworkConfig;



public class BaseClass {
	
	ExtentReports report;
	ExtentSparkReporter spark;
	static ExtentTest test;
	
	protected static FrameworkConfig config=ConfigFactory.create(FrameworkConfig.class);
	
	protected RequestSpecification givenSpec() {
		
		return RestAssured.given().baseUri(config.URL()).relaxedHTTPSValidation().accept(ContentType.JSON);
		
	}
	
	@BeforeTest(groups = {"Regression"})
	public void ReportSetup() {
		report=new ExtentReports();
		spark=new ExtentSparkReporter("./ExtentReport/Report.html");
		report.attachReporter(spark);
		report.setSystemInfo("Host Name", "API AUTOMATION");
		report.setSystemInfo("Environment", "TEST");
		report.setSystemInfo("User Name", "QA Tester");
		spark.config().setDocumentTitle("Regression API automation suite");
		spark.config().setReportName("API Automation Report");
		
	}
	
	@AfterMethod(groups={"Regression"})
	public void getReport(ITestResult result) {
		
		if(result.getStatus()==ITestResult.FAILURE) {
			test.log(Status.FAIL, "Test Case is Fail "+result.getName());
		}
		else if(result.getStatus()==ITestResult.SUCCESS){
			test.log(Status.PASS, "Test Case is Pass "+result.getName());
		}
		else if(result.getStatus()==ITestResult.SKIP) {
			test.log(Status.SKIP, "Test Case Skip");
		}
		else if(result.getStatus()==ITestResult.STARTED) {
			test.log(Status.INFO, "Test Case Started");
		}
	}
	
	@AfterTest(groups= {"Regression"})
	public void endTest() {
		report.flush();
		
	}
	
	@AfterSuite
	public void closeReport() {
		
	}

}
