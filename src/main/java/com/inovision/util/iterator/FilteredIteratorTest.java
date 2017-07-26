package com.inovision.util.iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import junit.framework.TestCase;

public class FilteredIteratorTest extends TestCase {

	static Filter<String> filter = new Filter<String>() {
		public boolean test(String element) {
			return element.startsWith("ABC");
		}
	};
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testFilteredIterator() {
		List<String> list = new ArrayList<String>();
		
		list.add("ABC 123");
		list.add("123 ABC");
		list.add("ABCDEF");
		list.add("123 ABC");
		
		Iterator<String> iter = list.iterator();
		Iterator<String> iter2 = new FilteredIterator<String>(iter, filter);
		while(iter2.hasNext()) {
			assertTrue(iter2.next().startsWith("ABC"));
		}
		
		try {
			List<String> list2 = new ArrayList<String>();
			list2.add("123");
			list2.add("345");
			Iterator<String> iter3 = new FilteredIterator<String>(list2.iterator(), filter);
			iter3.next();
			fail("Expected NoSuchElementException");
		} catch(NoSuchElementException e) {
			//Should throw NoSuchElementException
			//Success if reached here
		}
		
		try {
			iter2.remove();
			fail("Expected UnsupportedOperationException");
		} catch(UnsupportedOperationException e) {
			//Success if reached here
		}
	}
}
