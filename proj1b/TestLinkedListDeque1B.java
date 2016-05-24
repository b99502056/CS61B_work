/**
 * Created by LouisCho on 5/23/16.
 */

import static org.junit.Assert.*;
import org.junit.Test;


public class TestLinkedListDeque1B {
	@Test
	public void addFirstTest() {
		// Create a test deque {1, 2, 3, 4, 5} .
		StudentLinkedListDeque testDeque = new StudentLinkedListDeque<Integer>();
		testDeque.addLast(1);
		testDeque.addLast(2);
		testDeque.addLast(3);
		testDeque.addLast(4);
		testDeque.addLast(5);

		// Add 0 to the front of the deque.
		testDeque.addFirst(0);

		assertEquals(0, testDeque.removeFirst());
		assertEquals(1, testDeque.removeFirst());

	}

	@Test
	public void addLastTest() {
		// Create a test deque {1, 2, 3, 4, 5} .
		StudentLinkedListDeque testDeque = new StudentLinkedListDeque<Integer>();
		testDeque.addFirst(4);
		testDeque.addFirst(3);
		testDeque.addFirst(2);
		testDeque.addFirst(1);
		testDeque.addFirst(0);

		// Add 5 to the back of the deque.
		testDeque.addLast(5);

		assertEquals(5, testDeque.removeLast());
		assertEquals(4, testDeque.removeLast());

	}

	@Test
	public void isEmptyTest() {
		StudentLinkedListDeque testDeque = new StudentLinkedListDeque<Integer>();

		assertEquals(true, testDeque.isEmpty());

		testDeque.addFirst(100);

		assertEquals(false, testDeque.isEmpty());

		testDeque.removeFirst();
		assertEquals(true, testDeque.isEmpty());

	}

	@Test
	public void sizeTest() {
		StudentLinkedListDeque testDeque = new StudentLinkedListDeque<Integer>();
		assertEquals(0, testDeque.size());

		testDeque.addFirst(0);
		assertEquals(1, testDeque.size());
		testDeque.addLast(1);
		assertEquals(2, testDeque.size());
		testDeque.removeFirst();
		assertEquals(1, testDeque.size());
		testDeque.removeLast();
		assertEquals(0, testDeque.size());
		testDeque.removeFirst();
		assertEquals(0, testDeque.size());
	}

	@Test
	public void removeFirstTest() {
		// Create a test deque {1, 2} .
		StudentLinkedListDeque testDeque = new StudentLinkedListDeque<Integer>();
		testDeque.addFirst(1);
		testDeque.addLast(2);

		assertEquals(1, testDeque.removeFirst());
		assertEquals(2, testDeque.removeFirst());
		assertEquals(null, testDeque.removeFirst());

	}

	@Test
	public void removeLastTest() {
		// Create a test deque {1, 2} .
		StudentLinkedListDeque testDeque = new StudentLinkedListDeque<Integer>();
		testDeque.addFirst(1);
		testDeque.addLast(2);
		assertEquals(2, testDeque.removeLast());
		assertEquals(1, testDeque.removeLast());
		assertEquals(null, testDeque.removeLast());


	}

	@Test
	public void getTest() {
		// Create a test deque {1, 2, 3, 4, 5} .
		StudentLinkedListDeque testDeque = new StudentLinkedListDeque<Integer>();
		FailureSequence fs = new FailureSequence();

		/* This method returns a wrong value when trying to reach an item with
		 * an index larger than the length of the list. It returns some value
		 * other than null. */
		fs.addOperation(new DequeOperation("addLast", 1));
		testDeque.addLast(1);

		fs.addOperation(new DequeOperation("addLast", 2));
		testDeque.addLast(2);

		fs.addOperation(new DequeOperation("addLast", 3));
		testDeque.addLast(3);



		fs.addOperation(new DequeOperation("get", 0));
		assertEquals(fs.toString(), 1, testDeque.get(0));

		fs.addOperation(new DequeOperation("get", 2));
		assertEquals(fs.toString(), 3, testDeque.get(2));

		fs.addOperation(new DequeOperation("get", 3));
		assertEquals(fs.toString(), null, testDeque.get(3));

		fs.addOperation(new DequeOperation("get", 4));
		assertEquals(fs.toString(), null, testDeque.get(4));

	}

	public void main(String[] args) {
		jh61b.junit.TestRunner.runTests("failed", TestLinkedListDeque1B.class);
	}
}

