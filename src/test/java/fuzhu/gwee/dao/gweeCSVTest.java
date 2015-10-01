package fuzhu.gwee.dao;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class gweeCSVTest {
	private gweeCSV csv;
	
	@Before
	public void setUp() throws Exception {
		csv = new gweeCSV();
	}

	@Test
	public void testReadpassport() {
		csv.readpassport();
	}

}
