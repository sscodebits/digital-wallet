package com.shikha.fraudDetectorTest;

import static org.junit.Assert.*;

import org.junit.Test;

import com.shikha.fraudDetector.UserNode;
import com.shikha.fraudDetector.UserNodeImpl;


public class UserNodeImplTest  {
	
	public UserNodeImplTest(){}

	@Test
	public void testSimple() {
		UserNode un = new UserNodeImpl(1);
		assertEquals(1, un.getUserId());
	}
	
	@Test
	public void testComplex() {
		UserNode un1 = new UserNodeImpl(1);
		UserNode un2 = new UserNodeImpl(1);
		UserNode un3 = new UserNodeImpl(3);
		UserNode un4 = new UserNodeImpl(4);
		un1.addFriend(un3);
		un2.addFriend(un4);
		
		assertTrue(un1.equals(un2));
	}
}
