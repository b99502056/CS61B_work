/**
 * Created by LouisCho on 5/23/16.
 */

import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDeque1B {

	

	@Test
	public void addFirstTest() {
		// Create a test deque {1, 2, 3, 4, 5} .
		StudentArrayDeque testDeque = new StudentArrayDeque<Integer>();
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
		StudentArrayDeque testDeque = new StudentArrayDeque<Integer>();
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
		StudentArrayDeque testDeque = new StudentArrayDeque<Integer>();

		assertEquals(true, testDeque.isEmpty());

		testDeque.addFirst(100);

		assertEquals(false, testDeque.isEmpty());
	}

	@Test
	public void sizeTest() {
		StudentArrayDeque testDeque = new StudentArrayDeque<Integer>();
		assertEquals(0, testDeque.size());

		testDeque.addFirst(0);
		assertEquals(1, testDeque.size());
		testDeque.addLast(1);
		assertEquals(2, testDeque.size());
		testDeque.removeFirst();
		assertEquals(1, testDeque.size());
		testDeque.removeLast();
		assertEquals(0, testDeque.size());

		// Buggy when trying to remove items from an empty list.
		testDeque.removeFirst();


		FailureSequence fs = new FailureSequence();
		DequeOperation dequeOp1 = new DequeOperation("addFirst", 0);
		DequeOperation dequeOp2 = new DequeOperation("addLast", 1);
		DequeOperation dequeOp3 = new DequeOperation("removeFirst");
		DequeOperation dequeOp4 = new DequeOperation("removeLast");
		DequeOperation dequeOp5 = new DequeOperation("removeFirst");
		fs.addOperation(dequeOp1);
		fs.addOperation(dequeOp2);
		fs.addOperation(dequeOp3);
		fs.addOperation(dequeOp4);
		fs.addOperation(dequeOp5);

		assertEquals(fs.toString(), 0, testDeque.size());
	}

	@Test
	public void removeFirstTest() {
		// Create a test deque {1, 2} .
		StudentArrayDeque testDeque = new StudentArrayDeque<Integer>();
		testDeque.addLast(1);
		testDeque.addLast(2);

		assertEquals(1, testDeque.removeFirst());
		assertEquals(2, testDeque.removeFirst());
		assertEquals(null, testDeque.removeFirst());


	}

	@Test
	public void removeLastTest() {
		// Create a test deque {1, 2} .
		StudentArrayDeque testDeque = new StudentArrayDeque<Integer>();
		testDeque.addLast(1);
		testDeque.addLast(2);
		assertEquals(2, testDeque.removeLast());
		assertEquals(1, testDeque.removeLast());
		assertEquals(null, testDeque.removeLast());
	}

	@Test
	public void getTest() {
		// Create a test deque {1, 2, 3, 4, 5} .
		StudentArrayDeque testDeque = new StudentArrayDeque<Integer>();
		testDeque.addLast(1);
		testDeque.addLast(2);
		testDeque.addLast(3);
		testDeque.addLast(4);
		testDeque.addLast(5);

		assertEquals(1, testDeque.get(0));
		assertEquals(5, testDeque.get(4));
		assertEquals(null, testDeque.get(5));

	}

	public static void main(String[] args) {
		jh61b.junit.TestRunner.runTests("failed", TestArrayDeque1B.class);
	}

}
