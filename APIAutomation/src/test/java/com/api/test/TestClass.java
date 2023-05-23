package com.api.test;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import io.restassured.response.Response;

public class TestClass extends BaseClass {
	
	@Test
	public void SingleUser() {
		
		Response res=givenSpec().when().get("/api/users/2");
		
		test=report.createTest(" Validate Single User Test Case");
		System.out.println(res.getStatusCode());
		System.out.println(" SINGLE USER API CALL => ");
		System.out.println(res.getBody().asPrettyString());
		
		test.log(Status.INFO, "SINGLE USER API");
		test.log(Status.INFO, "Actual response code : "+res.getStatusCode());
		test.log(Status.INFO, MarkupHelper.createCodeBlock(res.getBody().asPrettyString()));
		
		
	}

}
