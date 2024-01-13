package org.dataprovider;

import org.testng.annotations.DataProvider;

public class DataProvidersTestData {

	@DataProvider(name = "Credentials")
	public Object[][] data() {
		return new Object[][] { { "standard_user", "secret_sauce" }, { "locked_out_user", "secret_sauce" },
				{ "abcccd", "secret_sauce" } };

	}

}
