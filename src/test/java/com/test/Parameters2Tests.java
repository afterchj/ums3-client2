/*
package com.test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.Suite.SuiteClasses;
@RunWith(Parameterized.class)
@SuiteClasses({})
public class Parameters2Tests {
	
	private String date;
	private String dateFormat;
	private String expectedDate;
	
	public Parameters2Tests(String date, String dateFormat, String expectedDate) {
		super();
		this.date = date;
		this.dateFormat = dateFormat;
		this.expectedDate = expectedDate;
	}

	@Parameters
	public static List<String[]> prepareParams() {
		String[][] params = new String[][] {
				{ "2014-12-12 10:15:39", "yyyy-MM-dd", "2014-12-12" },
				{ "2014-12-14 10:34:34", "yyyy-MM-dd HH:mm:ss",	"2014-12-14 10:34:34" },
				{ "2014-12-14 10:34:34", "HH:mm:ss", "10:34:34" } };
		return Arrays.asList(params);
	}

	@Test
	public void testParameterized() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = sdf.parse(this.date);
		SimpleDateFormat sdf2 = new SimpleDateFormat(this.dateFormat);
		String actual = sdf2.format(now);
		assertThat(actual, is(this.expectedDate));
	}
}
*/
