package fuzhu.idMaker;

import static org.junit.Assert.*;

import org.junit.Test;

public class CreateIDCardNoTest {

	@Test
	public void testGetRandomID() {
		CreateIDCardNo cre = new CreateIDCardNo();
		String randomID = cre.getRandomID();
//		System.out.println(randomID);
		assertEquals(18,randomID.length());
	}

}
