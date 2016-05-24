public class LinkedListDeque<Item> implements Deque<Item> {

	/* Create Node class as the building block. */
	public class Node {
		public Node prev; 
		public Item item;
		public Node next;

		public Node(Node p, Item i, Node n) {
			prev = p;
			item = i;
			next = n;
		}
	}

	/* Create a sentinel node. */
	private Node sentinel; 
	/* Keep record of the deque size. */
	private int size;
	/* Creates an empty linked list deque. */
	public LinkedListDeque() {
		sentinel = new Node(null, null, null);
		/* Link sentinel to itself at front and back
		 * to form a circular linkage. */
		sentinel.next = sentinel;
		sentinel.prev = sentinel;
		/* Initialize size. */
		size = 0;
	}

	/* Adds an item to the front of the Deque. */
	@Override
	public void addFirst(Item i) {
		/* Applicable even when the deque is empty. */
		Node oldFront = sentinel.next;
		Node newFront = new Node(sentinel, i, oldFront);
		/* Link old front back to new front. */
		oldFront.prev = newFront;
		/* Link sentinel to the new front. */
		sentinel.next = newFront;
		/* Increment size. */
		size += 1;
		return;
	}

	/* Adds an item to the back of the Deque. */
	@Override
	public void addLast(Item i) {
		/* Applicable even when the deque is empty. */
		Node oldBack = sentinel.prev;
		Node newBack = new Node(oldBack, i, sentinel);
		/* Link old back to the new back. */
		oldBack.next = newBack;
		/* Link sentinel to the new back. */
		sentinel.prev = newBack;
		/* Increment size. */
		size += 1;
		return;
	}

	/* Return true if deque is empty, false otherwise. */
	@Override
	public boolean isEmpty() {
		if (sentinel.next == sentinel) {
			return true;
		}
		return false;
	}
	/* Returns the number of items in the deque. */
	@Override
	public int size() {
		return size;
	}

	/* Prints the items in the deque from first to last,
	 * separated by a space. */
	@Override
	public void printDeque() {
		/* The current node at the beginning is the node 
		 * next to sentinel. */
		Node currentNode = sentinel.next;
		/* If the deque is empty, let the user know. */
		if (size == 0) {
			System.out.println("The deque is empty.");
			return;
		}

		for (int i = 0; i < size; i++) {
			System.out.print(currentNode.item + " ");
			currentNode = currentNode.next; 
		}
		return;
	}

	/* Remove and return the item at the front of the deque. 
	 * If no such item exists, return null. */
	@Override
	public Item removeFirst() {
		if (size == 0) {
			return null;
		}
		Node oldFront = sentinel.next;
		Node newFront = oldFront.next;
		Item oldFirstItem = oldFront.item;
		/* Relink the sentinel to the new front. */
		sentinel.next = newFront;
		/* Link the new front back to the sentinel. */
		newFront.prev = sentinel;
		/* Devrement the size. */
		size -= 1;

		return oldFirstItem;
	}

	/* Remove and return the item at the back of the deque.
	 * If no such item exists, return null. */
	@Override
	public Item removeLast() {
		if (size == 0) {
			return null;
		}
		Node oldBack = sentinel.prev;
		Node newBack = oldBack.prev;
		Item oldLastItem = oldBack.item;
		/* Relink the sentinel to the new back. */
		sentinel.prev = newBack;
		/* Link the new back to the sentinel. */
		newBack.next = sentinel;
		/* Decrement the size */
		size -= 1;

		return oldLastItem;
	}
	/* Gets the item at the given index, where 0 is the front,  
	 * 1 is the next item, and so forth. If no such item exists,
	 * return null. Must not alter the deque! 
	 * Iteration implementation. */
	@Override
	public Item get(int index) {
		if (index >= size) {
			return null;
		} else if (index < 0) {
			return null;
		} else {
			/* Iterate until hit the item at given index. */
			Node currentNode = sentinel;
			for (int i = 0; i < index; i++) {
				currentNode = currentNode.next;
			}
			return currentNode.item;
		}
	}

	/* The recursive version of get(). */
	public Item getRecursive(int index) {
		return positionInRecursion(index, sentinel.next);
	}
	/* Helper function for recursive version of get(). */
	private Item positionInRecursion(int index, Node p) {
		/* Base case, index == 0. */
		if (index == 0) {
			return p.item;
		}
		return positionInRecursion(index - 1, p.next);
	} 
}