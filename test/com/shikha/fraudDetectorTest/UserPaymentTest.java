package com.shikha.fraudDetectorTest;

import static org.junit.Assert.*;

import org.junit.Test;

import com.shikha.fraudDetector.UserPayment;

public class UserPaymentTest  {

	public UserPaymentTest() {}
	
	@Test
	public void testSimple() {
		UserPayment up = UserPayment.parsePayment("1/1/2016,1,2,12.2,asdf");
		assertNotNull(up);
	}
	@Test
	public void testMsg1() {
		UserPayment up = UserPayment.parsePayment("1/1/2016,1,2,12.2,asdf, df");
		assertNotNull(up);
	}
	@Test
	public void testMsg2() {
		UserPayment up = UserPayment.parsePayment("1/1/2016,1,2,12.2,asdf, df,sdf ");
		assertNotNull(up);
	}
	@Test
	public void testBadMsg1() {
		UserPayment up=null;
		try{
		 up = UserPayment.parsePayment("1/1/2016,1,2,12.2");
		} catch (IllegalArgumentException ae) {
			assertNull(up);
		}
	}
	@Test
	public void testBadMsg2() {
		UserPayment up=null;
		try{
		 up = UserPayment.parsePayment("1/1/2016,1,12.2,asdf");
		} catch (IllegalArgumentException ae) {
			assertNull(up);
		}
	}
	@Test
	public void testBadMsg3() {
		UserPayment up=null;
		try{
		 up = UserPayment.parsePayment("1/1/2016,1,2,12.2.3,asdf");
		} catch (IllegalArgumentException ae) {
			assertNull(up);
		}
	}
	@Test
	public void testValidMsg1() {
		UserPayment up = UserPayment.parsePayment("1/1/2016,1,2,12.2,asdf, df");
		boolean validup = up.getDateStamp().equals("1/1/2016") && up.getId1() == 1 && up.getId2() == 2 && Float.compare(up.getAmt(), (float)12.2) == 0;
		assertTrue(validup);
	}
}
