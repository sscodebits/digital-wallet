package com.shikha.fraudDetectorTest;
import static org.junit.Assert.*;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.shikha.fraudDetector.UserGraph;

@FixMethodOrder (MethodSorters.NAME_ASCENDING)
public class UserGraphTest {
	public void UserGraphTest() {}
	
	@Test
	public void testSimple() {
		UserGraph ug = UserGraph.getInstance();
		assertNotNull(ug);
	}
	@Test
	public void testAdd1() {
		UserGraph ug = UserGraph.getInstance();
		ug.addConnection(1, 2);
		int retVal = ug.BFS(1, 2, 1);
		assertEquals(1, retVal);
		
		retVal = ug.BFS(2, 1, 1);
		assertEquals(1, retVal);

		retVal = ug.BFS(1, 2, 2);
		assertEquals(1, retVal);
	}
	
	@Test
	public void testAdd2() {
		UserGraph ug = UserGraph.getInstance();
		ug.addConnection(3, 2);
		int retVal =0;
		retVal = ug.BFS(2, 3, 2);
		assertEquals(1, retVal);
		
		retVal = ug.BFS(1, 3, 1);
		assertEquals(-1, retVal);
		
		retVal = ug.BFS(1, 3, 2);
		assertEquals(2, retVal);
	}
	
	@Test
	public void testAdd3() {
		UserGraph ug = UserGraph.getInstance();
		ug.addConnection(3, 4);
		int retVal =0;
		retVal = ug.BFS(1, 4, 4);
		assertEquals(3, retVal);
		
		retVal = ug.BFS(1, 4, 2);
		assertEquals(-1, retVal);
		
		retVal = ug.BFS(2, 4, 2);
		assertEquals(2, retVal);
	}

	@Test
	public void testAdd4() {
		UserGraph ug = UserGraph.getInstance();
		ug.addConnection(5, 4);
		int retVal =0;
		retVal = ug.BFS(5, 4, 4);
		assertEquals(1, retVal);

		retVal = ug.BFS(1, 5, 4);
		assertEquals(4, retVal);
		
		retVal = ug.BFS(1, 5, 3);
		assertEquals(-1, retVal);
		
		retVal = ug.BFS(2, 5, 2);
		assertEquals(-1, retVal);

		retVal = ug.BFS(2, 5, 3);
		assertEquals(3, retVal);
	}

	@Test
	public void testAdd5() {
		UserGraph ug = UserGraph.getInstance();
		ug.addConnection(5, 6);
		int retVal =0;
		retVal = ug.BFS(6, 1, 4);
		assertEquals(-1, retVal);

		retVal = ug.BFS(6, 2, 4);
		assertEquals(4, retVal);
}
}
