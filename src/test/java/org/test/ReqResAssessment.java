package org.test;

import org.base.BaseClass;
import org.restUtils.RestApiUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

public class ReqResAssessment extends BaseClass {

	@BeforeClass
	public void setup() {

		test.log(Status.INFO, "Starting test case for Req Res site.");

	}

	@Test
	public void getList() {
		
		RestApiUtils.listUsersValidation();

		test.pass("Retrived Data from List of GET Method");

	}

	@Test
	private void getSingle() {
		
		RestApiUtils.singleUserValidation();

		test.pass("Retrived Data from Single GET Method");

	}

	@Test
	public void postUser() {
		

		RestApiUtils.postUserValidation();
		
		test.pass("Created Data for User using POST method");
	}
	
	@AfterClass
	public void closing() {

		test.log(Status.INFO, "Ending Test for Req Res site.");

	}

}
