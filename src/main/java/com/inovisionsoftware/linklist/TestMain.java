package com.inovisionsoftware.linklist;

import java.util.Stack;

public class TestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		reverseLinkList();
		Stack<Integer> s = new Stack<Integer>();
	}
	
	public static void reverseLinkList() {		
		//Create the link list
		LinkElement head = new LinkElement();
		head.data = "Head";
		LinkElement current = head;
		for(int i=2; i < 10; i++) {
			LinkElement e = new LinkElement();
			e.data = "Element " + i;
			current.next = e;
			current = e;
		}		
		LinkElement tail = new LinkElement();
		current.next = tail;
		tail.data = "Tail";
		tail.next = null;
		
		//print it out
		current = head;
		while(current != null) {
			System.out.println(current.data);
			current = current.next;
		}
		
		//now reverse it
		current = head.next;
		head.next = null;
		LinkElement prev = head;
		LinkElement next=current.next;
		LinkElement temp = null;
		do {
			current.next = prev;
			temp = next.next;
			next.next = current;
			prev = current;
			current = next;
			next = temp;
		} while(next != null);

		//print it out
		current = tail;
		while(current != null) {
			System.out.println(current.data);
			current = current.next;
		}
		
	}

}
